package com.polls.polls_backend.repository;

import com.polls.polls_backend.domain.Poll;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface IPollRepository {

  List<Poll> getAllPolls();

  Optional<Poll> getPollById(int id);

  Optional<Poll> getActivePoll();

  int createPoll(String question);

  void setPollActive(int id);

  void disableAllPolls();
}
