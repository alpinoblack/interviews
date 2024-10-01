package com.example.interviews.general.project2;

public interface RateLimiter {

  /**
   * Implement a simple Rate Limiter. It has only a single method called permitRequest()
   * which only determines if a message should pass through or not.
   * The Rate Limiter can't get any outside parameters. A code can use this Rate Limiter
   * by first calling this method and see if it gets a positive or negative response, negative
   * means blocking the request and dropping it.
   */
  boolean permitRequest();
}
