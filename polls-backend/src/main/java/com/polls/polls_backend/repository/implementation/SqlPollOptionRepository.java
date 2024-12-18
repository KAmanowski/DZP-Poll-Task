package com.polls.polls_backend.repository.implementation;

import com.polls.polls_backend.domain.Option;
import com.polls.polls_backend.exception.SqlRepositoryException;
import com.polls.polls_backend.repository.IPollOptionRepository;
import com.polls.polls_backend.repository.resultsetextractor.PollOptionResultSetExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SqlPollOptionRepository implements IPollOptionRepository {


  private static final String CREATE_POLL_OPTION = ""
      + "INSERT INTO dbo.Poll_Option (Poll_Id, Content, Deleted) "
      + "VALUES (:pollId, :content, 0)";

  private static final String DELETE_POLL_OPTION = ""
      + "UPDATE TOP(1) dbo.Poll_Option SET Deleted = 1 "
      + "WHERE Option_Id = :optionId AND Poll_Id = :pollId";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final PollOptionResultSetExtractor pollOptionResultSetExtractor;

  @Override
  public int addPollOption(int pollId, String content) {
    try {
      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      mapSqlParameterSource.addValue("pollId", pollId);
      mapSqlParameterSource.addValue("content", content);

      KeyHolder keyHolder = new GeneratedKeyHolder();
      namedParameterJdbcTemplate.update(CREATE_POLL_OPTION, mapSqlParameterSource, keyHolder);

      return keyHolder.getKey().intValue();
    } catch (Exception ex) {
      throw new SqlRepositoryException("Could not create poll option for poll " + pollId + " with content " + content,
          ex);
    }
  }

  @Override
  public void deletePollOption(int optionId, int pollId) {
    try {
      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      mapSqlParameterSource.addValue("optionId", optionId);
      mapSqlParameterSource.addValue("pollId", pollId);

      namedParameterJdbcTemplate.update(DELETE_POLL_OPTION, mapSqlParameterSource);
    } catch (Exception ex) {
      throw new SqlRepositoryException("Could not set poll option " + optionId + " as deleted.", ex);
    }
  }
}
