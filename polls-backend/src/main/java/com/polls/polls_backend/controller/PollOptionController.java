package com.polls.polls_backend.controller;

import com.polls.polls_backend.domain.Option;
import com.polls.polls_backend.domain.Poll;
import com.polls.polls_backend.exception.NotFoundException;
import com.polls.polls_backend.service.PollOptionService;
import com.polls.polls_backend.service.PollService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/poll/{pollId}/option")
@RequiredArgsConstructor
@Slf4j
public class PollOptionController {

    private final PollService pollService;
    private final PollOptionService pollOptionService;

    @PostMapping()
    public ResponseEntity<?> addPollOption(@RequestBody() String content,
                                                @PathVariable("pollId") int pollId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pollOptionService.addPollOption(pollId, content));
        } catch (NotFoundException ex) {
            log.error("Could not find pollId {} to add option with content: '{}'", pollId, content, ex);
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException ex) {
            log.error("Could not add option to poll {} with content '{}' as there are more than max options.", pollId,
                content, ex);
            return ResponseEntity.badRequest().body("Too many options in poll. Please delete some.");
        } catch (Exception ex) {
            log.error("Could not fetch all polls.", ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("{optionId}")
    public ResponseEntity<Option> addPollOption(@PathVariable("pollId") int pollId,
                                                @PathVariable("optionId") int optionId) {
        try {
            pollOptionService.deletePollOption(optionId, pollId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            log.error("Could not fetch all polls.", ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}
