package com.polls.polls_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Poll {

  private int id;
  private String question;
  private long createdAt;
  private long updatedAt;
  private boolean active;
  private List<Option> options;
  private int totalPollVotes;
}
