package com.example.interviews.general;

import java.util.LinkedHashMap;

/**
 * Based on <a href="https://leetcode.com/problems/lru-cache/description/">...</a>
 * Correct Solution
 * Performance: Beats 33%
 * Memory: Beats 5.06%
 * Based on the LinkedHashMap, not sure if the remove is indeed in O(1)
 * Generic solution, in LeetCode the key and value are of type Integer
 * In case a key is not found returns null, in LeetCode supposed to return -1
 */
public class LRUCache<K, V> { //should try another solution with custom doubly linked list

  private final int capacity;
  private final LinkedHashMap<K, V> map;

  public LRUCache(final int capacity) {
    this.capacity = capacity;
    map = new LinkedHashMap<>(capacity);
  }

  public V get(final K key) {
    final V value = map.get(key);
    if (value != null) {
      map.remove(key);
      map.put(key, value);
    }
    return value;
  }

  public void put(final K key, final V value) {
    if (map.containsKey(key)) {
      map.remove(key);
    } else {
      if (map.entrySet().size() == capacity) {
        map.remove(map.firstEntry().getKey());
      }
    }
    map.put(key, value);
  }
}
