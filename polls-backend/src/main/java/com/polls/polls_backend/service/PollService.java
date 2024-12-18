package com.polls.polls_backend.service;

import com.polls.polls_backend.domain.Poll;
import com.polls.polls_backend.exception.NotFoundException;
import com.polls.polls_backend.repository.IPollOptionRepository;
import com.polls.polls_backend.repository.IPollRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PollService {

    private final IPollRepository pollRepository;
    private final ValidationService validationService;

    public List<Poll> getAllPolls() {
        return pollRepository.getAllPolls();
    }

    public Optional<Poll> getPollById(int id) {
        return pollRepository.getPollById(id);
    }

    public Optional<Poll> getActivePoll() {
        return pollRepository.getActivePoll();
    }

    public Poll createPoll(String question) {
        int pollId = pollRepository.createPoll(question);
        return getPollById(pollId).get();
    }

    public void setPollActive(int id) throws NotFoundException {
        Poll poll = validationService.validatePollExists(id);
        validationService.validateMinimumPollOptions(poll);

        pollRepository.disableAllPolls();
        pollRepository.setPollActive(id);
    }
}
