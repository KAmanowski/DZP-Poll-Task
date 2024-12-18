package com.polls.polls_backend.repository.resultsetextractor;

import com.polls.polls_backend.domain.Option;
import com.polls.polls_backend.domain.Poll;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PollOptionResultSetExtractor implements ResultSetExtractor<List<Option>> {


  @Override
  public List<Option> extractData(ResultSet rs) throws SQLException, DataAccessException {
    List<Option> polls = new ArrayList<>();

    while (rs.next()) {
      polls.add(Option.builder()
          .id(rs.getInt("Option_Id"))
          .pollId(rs.getInt("Poll_Id"))
          .content(rs.getString("Content"))
          .deleted(rs.getBoolean("Deleted"))
          .build());
    }

    return polls;
  }
}
