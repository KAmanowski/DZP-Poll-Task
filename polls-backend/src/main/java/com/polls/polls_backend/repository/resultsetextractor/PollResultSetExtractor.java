package com.polls.polls_backend.repository.resultsetextractor;

import com.polls.polls_backend.domain.Poll;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PollResultSetExtractor implements ResultSetExtractor<List<Poll>> {


  @Override
  public List<Poll> extractData(ResultSet rs) throws SQLException, DataAccessException {
    List<Poll> polls = new ArrayList<>();

    while (rs.next()) {
      polls.add(Poll.builder()
          .id(rs.getInt("Poll_Id"))
          .question(rs.getString("Question"))
          .createdAt(rs.getTimestamp("Created_At").getTime())
          .updatedAt(rs.getTimestamp("Updated_At").getTime())
          .active(rs.getBoolean("Active"))
          .build());
    }

    return polls;
  }
}
