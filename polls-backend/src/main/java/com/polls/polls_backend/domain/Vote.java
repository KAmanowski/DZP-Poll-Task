package com.polls.polls_backend.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Vote {

  private int id;
  private int pollId;
  private long votedAt;
  private Option option;
}
