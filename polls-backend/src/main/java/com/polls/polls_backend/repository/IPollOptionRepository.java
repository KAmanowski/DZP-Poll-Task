package com.polls.polls_backend.repository;

import com.polls.polls_backend.domain.Option;
import com.polls.polls_backend.domain.Poll;

import java.util.List;
import java.util.Optional;

public interface IPollOptionRepository {

  int addPollOption(int pollId, String content);

  void deletePollOption(int optionId, int pollId);
}
