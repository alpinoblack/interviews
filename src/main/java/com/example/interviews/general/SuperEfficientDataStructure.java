package com.example.interviews.general;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

/**
 * Design a data structure with 3 methods, set(int index, T value)
 * T get(int index) and setAll(T value)
 * setAll sets all the available values to the given value.
 * All methods must have a time complexity of O(1)
 */
public class SuperEfficientDataStructure<T> {
  private T lastSetAllValue = null;
  private Instant lastSetAllTimestamp = null;

  private record ValAndSetTimestamp<T>(T value, Instant setTimestamp) {}

  private final Map<Integer, ValAndSetTimestamp<T>> map = new HashMap<>();

  public void set(int index, T value) {
    map.put(index, new ValAndSetTimestamp<>(value, Instant.now()));
  }

  @Nullable
  public T get(int index) {
    if (map.containsKey(index)) {
      final ValAndSetTimestamp<T> valueAndLastSetTimestamp = map.get(index);
      if (lastSetAllTimestamp == null) {
        return valueAndLastSetTimestamp.value;
      } else if (valueAndLastSetTimestamp.setTimestamp.isAfter(lastSetAllTimestamp)) {
        return valueAndLastSetTimestamp.value;
      } else {
        return lastSetAllValue;
      }
    } else {
      return lastSetAllValue;
    }
  }

  public void setAll(T value) {
    this.lastSetAllTimestamp = Instant.now();
    this.lastSetAllValue = value;
  }

}
