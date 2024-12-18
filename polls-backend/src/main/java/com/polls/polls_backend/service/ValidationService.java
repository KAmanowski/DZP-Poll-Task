package com.polls.polls_backend.service;

import com.polls.polls_backend.domain.Poll;
import com.polls.polls_backend.exception.NotFoundException;
import com.polls.polls_backend.repository.IPollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@Service
public class ValidationService {

  @Value("${poll.min.options}")
  private int MIN_POLL_OPTIONS;

  @Value("${poll.max.options}")
  private int MAX_POLL_OPTIONS;

  private final IPollRepository pollRepository;

  @Autowired
  public ValidationService(IPollRepository pollRepository) {
    this.pollRepository = pollRepository;
  }

  public Poll validatePollExists(int pollId) throws NotFoundException {
    Optional<Poll> poll = pollRepository.getPollById(pollId);
    if (poll.isEmpty()) {
      throw new NotFoundException("Could not find poll with id: " + pollId);
    }

    return poll.get();
  }

  public void validateMaximumPollOptions(Poll poll) {
    if (!CollectionUtils.isEmpty(poll.getOptions()) && poll.getOptions().size() > (MAX_POLL_OPTIONS - 1)) {
      throw new IllegalStateException("Poll doesn't meet maximum option requirement.");
    }
  }

  public void validateMinimumPollOptions(Poll poll) {
    if (CollectionUtils.isEmpty(poll.getOptions()) || poll.getOptions().size() < (MIN_POLL_OPTIONS + 1)) {
      throw new IllegalStateException("Poll doesn't meet minimum option requirement.");
    }
  }

  public void validatePollIsActive(Poll poll) {
    if (!poll.isActive()) {
      throw new IllegalStateException("Poll isn't active.");
    }
  }

  public void validateOptionExists(Poll poll, int optionId) {
    if (CollectionUtils.isEmpty(poll.getOptions())
        || poll.getOptions().stream().noneMatch(option -> option.getId() == optionId)) {
      throw new IllegalStateException("Poll option doesn't exist.");
    }
  }
}
