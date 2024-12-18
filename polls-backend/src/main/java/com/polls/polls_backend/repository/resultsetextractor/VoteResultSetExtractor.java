package com.polls.polls_backend.repository.resultsetextractor;

import com.polls.polls_backend.domain.Option;
import com.polls.polls_backend.domain.Poll;
import com.polls.polls_backend.domain.Vote;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class VoteResultSetExtractor implements ResultSetExtractor<List<Vote>> {

  @Override
  public List<Vote> extractData(ResultSet rs) throws SQLException, DataAccessException {
    List<Vote> votes = new ArrayList<>();

    while (rs.next()) {
      votes.add(Vote.builder()
          .id(rs.getInt("Vote_Id"))
          .pollId(rs.getInt("Poll_Id"))
          .votedAt(rs.getTimestamp("Voted_At").getTime())
          .option(Option.builder()
              .id(rs.getInt("Option_Id"))
              .content(rs.getString("Content"))
              .deleted(rs.getBoolean("Deleted"))
              .build())
          .build());
    }

    return votes;
  }
}
