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
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/poll")
@RequiredArgsConstructor
@Slf4j
public class PollController {

    private final PollService pollService;

    @GetMapping
    public ResponseEntity<List<Poll>> getAllPolls() {
        try {
            return ResponseEntity.ok(pollService.getAllPolls());
        } catch (Exception ex) {
            log.error("Could not fetch all polls.", ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{pollId}")
    public ResponseEntity<Poll> getPollById(@PathVariable("pollId") int pollId) {
        try {
            return pollService.getPollById(pollId).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception ex) {
            log.error("Could not fetch all polls.", ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<Poll> getActivePoll() {
        try {
            return pollService.getActivePoll().map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception ex) {
            log.error("Could not fetch all polls.", ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Poll> createPoll(@RequestBody() String question) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pollService.createPoll(question));
        } catch (Exception ex) {
            log.error("Could not create poll with question '{}'", question, ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{pollId}/active")
    public ResponseEntity<?> setAsActive(@PathVariable("pollId") int pollId) {
        try {
            pollService.setPollActive(pollId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalStateException ex) {
            log.error("Could not make poll {} active as there not enough options.", pollId, ex);
            return ResponseEntity.badRequest().body("Too little options in poll. Please add some.");
        } catch (NotFoundException ex) {
            log.error("Could not find pollId {} to make active.", pollId, ex);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Could not set poll {} to active.", pollId, ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}
