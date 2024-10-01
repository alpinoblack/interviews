package com.example.interviews.general.project2;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ArrayBlockingQueue;

@SuppressWarnings("unused")
public class SimpleRateLimiterImpl implements RateLimiter {

  private final static int RATE_LIMITER_CAPACITY = 1000;
  private final static Duration RATE_LIMIT_TIME_SPAN = Duration.ofMinutes(1L);
  private final ArrayBlockingQueue<Instant> timestamps = new ArrayBlockingQueue<>(RATE_LIMITER_CAPACITY);

  @Override
  public boolean permitRequest() {
    final var currentTime = Instant.now();
    var shouldPermitRequest = true;
    if (timestamps.size() == RATE_LIMITER_CAPACITY) {
      shouldPermitRequest = !checkIfGapBetweenTimestampsIsBiggerThanThreshold(currentTime);
    }
    timestamps.poll();
    timestamps.add(currentTime);
    return shouldPermitRequest;
  }

  private boolean checkIfGapBetweenTimestampsIsBiggerThanThreshold(final Instant currentTime) {
    assert timestamps.peek() != null;
    return (currentTime.toEpochMilli() - timestamps.peek().toEpochMilli()) > RATE_LIMIT_TIME_SPAN.toMillis();
  }
}
