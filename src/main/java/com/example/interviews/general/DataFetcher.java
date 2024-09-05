package com.example.interviews.general;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataFetcher {

  private static final ExecutorService executor = Executors.newFixedThreadPool(4);
  private static final Random rand = new Random();

  private String fetchData(final String input) throws InterruptedException {
    final boolean shouldFail = rand.nextBoolean();
    final int howMuchTimeToWaitSeconds = rand.nextInt(9);
    Thread.sleep(Duration.ofSeconds(howMuchTimeToWaitSeconds));
    System.out.println(input + " slept for " + howMuchTimeToWaitSeconds + " seconds");
    if (shouldFail) {
      System.out.println("failed");
      throw new IllegalArgumentException("sorry");
    } else {
      return input + " success";
    }
  }

  public List<String> fetchFromSources(final List<String> inputs) {
    final List<CompletableFuture<String>> futures = new ArrayList<>(inputs.size());
    for (final String input: inputs) {
      futures.add(

          CompletableFuture.supplyAsync(() -> {
            try {
              return fetchData(input);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          }, executor).handleAsync((value, e) ->
              Objects.requireNonNullElse(value, "problem"))

      );
    }

    final CompletableFuture<List<String>> unifiedFuture = CompletableFuture.allOf(
        futures.toArray(new CompletableFuture[0])).thenApply(
        v -> futures.stream().map(CompletableFuture::join).toList()
    );

    return unifiedFuture.join();
  }

  public static void main(String[] args) {
    final var dataFetcher = new DataFetcher();
    long start = System.currentTimeMillis();
    final List<String> output = dataFetcher.fetchFromSources(List.of("1", "2", "5"));
    long end = System.currentTimeMillis();
    System.out.println(output);
    System.out.println("total time is " + (end - start));
    executor.close();
  }


}
