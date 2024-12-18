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
public class PollWithOptionsResultSetExtractor implements ResultSetExtractor<Poll> {

  @Override
  public Poll extractData(ResultSet rs) throws SQLException, DataAccessException {
    Poll poll = null;
    List<Option> options = new ArrayList<>();

    while (rs.next()) {
      if (poll == null) {
        poll = Poll.builder()
            .id(rs.getInt("Poll_Id"))
            .question(rs.getString("Question"))
            .createdAt(rs.getTimestamp("Created_At").getTime())
            .updatedAt(rs.getTimestamp("Updated_At").getTime())
            .active(rs.getBoolean("Active"))
            .build();
      }

      options.add(Option.builder()
          .id(rs.getInt("Option_Id"))
          .pollId(rs.getInt("Poll_Id"))
          .content(rs.getString("Content"))
          .deleted(rs.getBoolean("Deleted"))
          .currentVoteCount(rs.getInt("Total_Votes"))
          .build());
    }

    if (poll != null) {
      poll.setOptions(options);
      // Only count the non-deleted ones to the total
      poll.setTotalPollVotes(options.stream().filter(option -> !option.isDeleted())
          .mapToInt(Option::getCurrentVoteCount).sum());
    }

    return poll;
  }
}
