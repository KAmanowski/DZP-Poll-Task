package com.polls.polls_backend.repository;

import com.polls.polls_backend.domain.Poll;
import com.polls.polls_backend.domain.Vote;

import java.util.List;
import java.util.Optional;

public interface IVoteRepository {

  void createVote(int optionId);

  List<Vote> fetchAllPollVotes(int pollId);
}
