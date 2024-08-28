package com.example.interviews.general;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LRUCacheTest {

  @Test //according to a test scenario in LeetCode
  public void test_LRUCacheJava_scenario1() {
    final var lruCache = new LRUCache<Integer, Integer>(2);
    assertThat(lruCache.get(2)).isNull();
    lruCache.put(2, 6);
    assertThat(lruCache.get(1)).isNull();
    lruCache.put(1,5);
    lruCache.put(1,2);
    assertThat(lruCache.get(1)).isEqualTo(2);
    assertThat(lruCache.get(2)).isEqualTo(6);
  }

  @Test //according to a test scenario in LeetCode
  public void test_LRUCacheJava_scenario2() {
    final var lruCache = new LRUCache<Integer, Integer>(2);
    lruCache.put(2,1);
    lruCache.put(1,1);
    lruCache.put(2,3);
    lruCache.put(4,1);
    assertThat(lruCache.get(1)).isNull();
    assertThat(lruCache.get(2)).isEqualTo(3);
  }

}