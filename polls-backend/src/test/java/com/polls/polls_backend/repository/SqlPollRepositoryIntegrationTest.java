package com.polls.polls_backend.repository;

import com.polls.polls_backend.PollsBackendIntegrationTest;
import com.polls.polls_backend.repository.implementation.SqlPollRepository;
import com.polls.polls_backend.utils.DatabaseUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@PollsBackendIntegrationTest
public class SqlPollRepositoryIntegrationTest {

  @Autowired
  private SqlPollRepository sqlPollRepository;

  @Autowired
  private DatabaseUtils databaseUtils;

  @BeforeEach
  void setUp() {
    databaseUtils.cleanTables();
  }

  @Test
  @DisplayName("Test saving a new poll")
  public void savePoll_success() {
    assertEquals(0, databaseUtils.getAllPolls().size());

    sqlPollRepository.createPoll("A poll question?");

    assertEquals(1, databaseUtils.getAllPolls().size());
  }

  @Test
  @DisplayName("Test fetching all polls")
  public void fetchAllPolls_success() {
    assertEquals(0, sqlPollRepository.getAllPolls().size());

    databaseUtils.createPoll("A poll question?");

    assertEquals(1, sqlPollRepository.getAllPolls().size());
  }
}
