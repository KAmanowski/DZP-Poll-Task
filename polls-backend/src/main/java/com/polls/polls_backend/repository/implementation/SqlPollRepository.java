package com.polls.polls_backend.repository.implementation;

import com.polls.polls_backend.domain.Poll;
import com.polls.polls_backend.exception.SqlRepositoryException;
import com.polls.polls_backend.repository.IPollRepository;
import com.polls.polls_backend.repository.resultsetextractor.PollResultSetExtractor;
import com.polls.polls_backend.repository.resultsetextractor.PollWithOptionsResultSetExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SqlPollRepository implements IPollRepository {

  private static final String FETCH_ALL_POLL = ""
      + "SELECT Poll_Id, Question, Created_At, Updated_At, Active "
      + "FROM dbo.Poll (NOLOCK) ";

  private static final String FETCH_POLL = ""
      + "SELECT p.Poll_Id, p.Question, p.Created_At, p.Updated_At, p.Active, "
      + "po.Option_Id, po.Poll_Id, po.Content, po.Deleted, "
      + "COUNT(v.Option_Id) AS Total_Votes "
      + "FROM dbo.Poll p (NOLOCK) "
      + "LEFT JOIN dbo.Poll_Option po (NOLOCK) ON p.Poll_Id = po.Poll_Id "
      + "LEFT JOIN dbo.Vote v (NOLOCK) ON po.Option_Id = v.Option_Id "
      + "WHERE p.Poll_Id = :pollId "
      + "GROUP BY p.Poll_Id, p.Question, p.Created_At, p.Updated_At, p.Active, po.Option_Id, po.Poll_Id, po.Content, "
      + "po.Deleted";

  private static final String FETCH_ACTIVE_POLL = ""
      + "SELECT p.Poll_Id, p.Question, p.Created_At, p.Updated_At, p.Active, "
      + "po.Option_Id, po.Poll_Id, po.Content, po.Deleted, "
      + "COUNT(v.Option_Id) AS Total_Votes "
      + "FROM dbo.Poll p (NOLOCK) "
      + "LEFT JOIN dbo.Poll_Option po (NOLOCK) ON p.Poll_Id = po.Poll_Id "
      + "LEFT JOIN dbo.Vote v (NOLOCK) ON po.Option_Id = v.Option_Id "
      + "WHERE p.Active = 1 "
      + "GROUP BY p.Poll_Id, p.Question, p.Created_At, p.Updated_At, p.Active, po.Option_Id, po.Poll_Id, po.Content, "
      + "po.Deleted";

  private static final String CREATE_POLL = ""
      + "INSERT INTO dbo.Poll (Question, Active) "
      + "VALUES (:question, 0)";

  private static final String SET_ACTIVE = ""
      + "UPDATE dbo.Poll SET Active = 1, Updated_At = CURRENT_TIMESTAMP WHERE Poll_Id = :pollId";

  private static final String SET_ALL_INACTIVE = ""
      + "UPDATE dbo.Poll SET Active = 0";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final PollWithOptionsResultSetExtractor pollWithOptionsResultSetExtractor;
  private final PollResultSetExtractor pollResultSetExtractor;

  @Override
  public List<Poll> getAllPolls() {
    try {
      return namedParameterJdbcTemplate.query(FETCH_ALL_POLL, pollResultSetExtractor);
    } catch (Exception ex) {
      throw new SqlRepositoryException("Could not fetch all polls.", ex);
    }
  }

  @Override
  public Optional<Poll> getPollById(int id) {
    try {
      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      mapSqlParameterSource.addValue("pollId", id);

      return Optional.ofNullable(namedParameterJdbcTemplate.query(FETCH_POLL,
          mapSqlParameterSource, pollWithOptionsResultSetExtractor));
    } catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    } catch (Exception ex) {
      throw new SqlRepositoryException("Could not get poll with Id " + id, ex);
    }
  }

  @Override
  public Optional<Poll> getActivePoll() {
    try {
      return Optional.ofNullable(namedParameterJdbcTemplate.query(FETCH_ACTIVE_POLL, pollWithOptionsResultSetExtractor));
    } catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    } catch (Exception ex) {
      throw new SqlRepositoryException("Could not get active poll.", ex);
    }
  }

  @Override
  public int createPoll(String question) {
    try {
      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      mapSqlParameterSource.addValue("question", question);

      KeyHolder keyHolder = new GeneratedKeyHolder();
      namedParameterJdbcTemplate.update(CREATE_POLL, mapSqlParameterSource, keyHolder);

      return keyHolder.getKey().intValue();
    } catch (Exception ex) {
      throw new SqlRepositoryException("Could not create poll with question: " + question, ex);
    }
  }

  @Override
  public void setPollActive(int id) {
    try {
      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      mapSqlParameterSource.addValue("pollId", id);

      namedParameterJdbcTemplate.update(SET_ACTIVE, mapSqlParameterSource);
    } catch (Exception ex) {
      throw new SqlRepositoryException("Could not set poll" + id + " active.", ex);
    }
  }

  @Override
  public void disableAllPolls() {
    try {
      namedParameterJdbcTemplate.update(SET_ALL_INACTIVE, new MapSqlParameterSource());
    } catch (Exception ex) {
      throw new SqlRepositoryException("Could not set all polls inactive.", ex);
    }
  }
}
