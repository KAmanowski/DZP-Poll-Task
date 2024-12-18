package com.polls.polls_backend.service;

import com.polls.polls_backend.domain.Poll;
import com.polls.polls_backend.domain.Vote;
import com.polls.polls_backend.exception.NotFoundException;
import com.polls.polls_backend.repository.IVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {

  private final IVoteRepository voteRepository;
  private final ValidationService validationService;

  public void createVote(int optionId, int pollId) throws NotFoundException {
    Poll poll = validationService.validatePollExists(pollId);
    validationService.validatePollIsActive(poll);
    validationService.validateOptionExists(poll, optionId);

    voteRepository.createVote(optionId);
  }

  public List<Vote> fetchAllVotesForPoll(int pollId) throws NotFoundException {
    validationService.validatePollExists(pollId);
    return voteRepository.fetchAllPollVotes(pollId);
  }
}
