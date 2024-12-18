package com.polls.polls_backend.utils;

import com.polls.polls_backend.domain.Poll;
import com.polls.polls_backend.repository.resultsetextractor.PollResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class DatabaseUtils {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final PollResultSetExtractor pollResultSetExtractor;

  @Autowired
  public DatabaseUtils(NamedParameterJdbcTemplate namedParameterJdbcTemplate, PollResultSetExtractor pollResultSetExtractor) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.pollResultSetExtractor = pollResultSetExtractor;
  }

  public void cleanTables() {
    namedParameterJdbcTemplate.update("DELETE FROM dbo.Vote", new HashMap<>());
    namedParameterJdbcTemplate.update("DELETE FROM dbo.Poll_Option", new HashMap<>());
    namedParameterJdbcTemplate.update("DELETE FROM dbo.Poll", new HashMap<>());
  }

  public void createPoll(String question) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("question", question);

    String sql = ""
        + "INSERT INTO dbo.Poll (Question, Active) "
        + "VALUES (:question, 0)";

    namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
  }

  public List<Poll> getAllPolls() {
    String sql = ""
        + "SELECT Poll_Id, Question, Created_At, Updated_At, Active "
        + "FROM dbo.Poll (NOLOCK) ";

    return namedParameterJdbcTemplate.query(sql, pollResultSetExtractor);
  }
}
