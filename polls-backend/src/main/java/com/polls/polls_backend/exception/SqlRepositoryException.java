package com.polls.polls_backend.exception;

public class SqlRepositoryException extends RuntimeException {

  public SqlRepositoryException(String message) {
    super(message);
  }

  public SqlRepositoryException(String message, Throwable cause) {
    super(message, cause);
  }
}
