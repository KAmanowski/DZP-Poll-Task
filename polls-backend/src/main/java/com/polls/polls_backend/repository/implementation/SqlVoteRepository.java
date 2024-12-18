package com.polls.polls_backend.repository.implementation;

import com.polls.polls_backend.domain.Poll;
import com.polls.polls_backend.domain.Vote;
import com.polls.polls_backend.exception.SqlRepositoryException;
import com.polls.polls_backend.repository.IPollRepository;
import com.polls.polls_backend.repository.IVoteRepository;
import com.polls.polls_backend.repository.resultsetextractor.PollResultSetExtractor;
import com.polls.polls_backend.repository.resultsetextractor.PollWithOptionsResultSetExtractor;
import com.polls.polls_backend.repository.resultsetextractor.VoteResultSetExtractor;
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
public class SqlVoteRepository implements IVoteRepository {

  private static final String CREATE_VOTE = ""
      + "INSERT INTO dbo.Vote (Option_Id, Voted_At) "
      + "VALUES (:optionId, CURRENT_TIMESTAMP)";

  private static final String FETCH_ALL_POLL_VOTES = ""
      + "SELECT v.Vote_Id, v.Option_Id, v.Voted_At, po.Poll_Id, po.Content, po.Deleted "
      + "FROM dbo.Vote v (NOLOCK) "
      + "LEFT JOIN dbo.Poll_Option po (NOLOCK) ON v.Option_Id = po.Option_Id "
      + "WHERE po.Poll_Id = :pollId";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final VoteResultSetExtractor voteResultSetExtractor;

  @Override
  public void createVote(int optionId) {
    try {
      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      mapSqlParameterSource.addValue("optionId", optionId);

      namedParameterJdbcTemplate.update(CREATE_VOTE, mapSqlParameterSource);
    } catch (Exception ex) {
      throw new SqlRepositoryException("Could not create vote for option: " + optionId, ex);
    }
  }

  @Override
  public List<Vote> fetchAllPollVotes(int pollId) {
    try {
      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      mapSqlParameterSource.addValue("pollId", pollId);

      return namedParameterJdbcTemplate.query(FETCH_ALL_POLL_VOTES, mapSqlParameterSource, voteResultSetExtractor);
    } catch (Exception ex) {
      throw new SqlRepositoryException("Could not fetch votes for poll " + pollId, ex);
    }
  }
}
