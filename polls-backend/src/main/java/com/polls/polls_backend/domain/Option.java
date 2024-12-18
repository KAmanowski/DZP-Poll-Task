package com.polls.polls_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Option {

  private int id;
  private int pollId;
  private String content;
  private boolean deleted;
  private int currentVoteCount;
}
