package com.polls.polls_backend.service;

import com.polls.polls_backend.domain.Option;
import com.polls.polls_backend.domain.Poll;
import com.polls.polls_backend.exception.NotFoundException;
import com.polls.polls_backend.repository.IPollOptionRepository;
import com.polls.polls_backend.repository.IPollRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PollOptionService {

    private final IPollOptionRepository optionRepository;
    private final ValidationService validationService;

    public Option addPollOption(int pollId, String content) throws NotFoundException {
        Poll poll = validationService.validatePollExists(pollId);
        validationService.validateMaximumPollOptions(poll);

        int optionId = optionRepository.addPollOption(pollId, content);
        return Option.builder()
            .id(optionId)
            .content(content)
            .pollId(pollId)
            .build();
    }

    public void deletePollOption(int optionId, int pollId) {
        optionRepository.deletePollOption(optionId, pollId);
    }
}
