package com.polls.polls_backend.controller;

import com.polls.polls_backend.domain.Option;
import com.polls.polls_backend.domain.Poll;
import com.polls.polls_backend.exception.NotFoundException;
import com.polls.polls_backend.service.PollOptionService;
import com.polls.polls_backend.service.PollService;
import com.polls.polls_backend.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/api/poll/{pollId}/option/{optionId}/vote")
    public ResponseEntity<?> submitVote(@PathVariable("pollId") int pollId,
                                           @PathVariable("optionId") int optionId) {
        try {
            voteService.createVote(optionId, pollId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException ex) {
            log.error("Could not find pollId {} to vote for.", pollId, ex);
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException ex) {
            log.error("Could not vote for poll {} as it's not active or option {} doesn't exist.", pollId, optionId, ex);
            return ResponseEntity.badRequest().body("Poll is not active or option doesn't exist.");
        } catch (Exception ex) {
            log.error("Could not vote for poll {} option {} due to error.", pollId, optionId, ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/api/poll/{pollId}/votes")
    public ResponseEntity<?> fetchAllPollVotes(@PathVariable("pollId") int pollId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(voteService.fetchAllVotesForPoll(pollId));
        } catch (NotFoundException ex) {
            log.error("Could not find pollId {} to fetch votes for.", pollId, ex);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Could not fetch votes for poll {}.", pollId, ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}
