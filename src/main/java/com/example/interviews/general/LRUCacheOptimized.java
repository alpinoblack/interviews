package com.example.interviews.general;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.com/problems/lru-cache/description/">...</a>
 */
public class LRUCacheOptimized<K, V> { //still abysmal results

  private final int capacity;
  private final DoublyLinkedListNode<K, V> head;
  private final DoublyLinkedListNode<K, V> tail;
  private final Map<K, DoublyLinkedListNode<K, V>> map;

  public LRUCacheOptimized(final int capacity) {
    this.capacity = capacity;
    this.map = new HashMap<>(capacity);
    this.head = new DoublyLinkedListNode<>(); //dummy head
    this.tail = new DoublyLinkedListNode<>(); //dummy tail
    head.next = tail;
    tail.prev = head;
  }

  private void moveToStart(final DoublyLinkedListNode<K, V> node) {
    node.next = head.next;
    node.prev = head;
    head.next.prev = node;
    head.next = node;
  }

  private void removeFromList(final DoublyLinkedListNode<K, V> node) {
    final var prev = node.prev;
    final var next = node.next;
    node.next = null;
    node.prev = null;
    if (prev != null) {
      prev.next = next;
    }
    if (next != null) {
      next.prev = prev;
    }
  }

  private DoublyLinkedListNode<K, V> removeFromTail() {
    final var removedNode = tail.prev;
    tail.prev = removedNode.prev;
    removedNode.prev.next = tail;
    removedNode.prev = null;
    removedNode.next = null;
    return removedNode;
  }

  public V get(final K key) {
    if (!map.containsKey(key)) {
      return null;
    }
    final DoublyLinkedListNode<K, V> reqNode = map.get(key);
    removeFromList(reqNode);
    moveToStart(reqNode);
    return reqNode.value;
  }

  public void put(final K key, final V value) {

    if (map.containsKey(key)) {
      removeFromList(map.get(key));
    }

    final var newNode = new DoublyLinkedListNode<>(key, value);
    moveToStart(newNode);
    map.put(key, newNode);

    if (map.size() > capacity) {
      final DoublyLinkedListNode<K, V> removedNode = removeFromTail();
      map.remove(removedNode.key);
    }
  }

  private static class DoublyLinkedListNode<K, V> {

    K key = null;
    V value = null;
    DoublyLinkedListNode<K, V> next = null;
    DoublyLinkedListNode<K, V> prev = null;

    private DoublyLinkedListNode(final K key, final V value) {
      this.key = key;
      this.value = value;
    }

    private DoublyLinkedListNode() {
    }

  }

  public static void main(String[] args) {
    LRUCacheOptimized<Integer, Integer> lRUCache = new LRUCacheOptimized<>(2);
    lRUCache.put(1, 1); // cache is {1=1}
    lRUCache.put(2, 2); // cache is {1=1, 2=2}
    System.out.println(lRUCache.get(1));    // return 1
    lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
    System.out.println(lRUCache.get(2));    // returns -1 (not found)
    lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
    System.out.println(lRUCache.get(1));    // return -1 (not found)
    System.out.println(lRUCache.get(3));    // return 3
    System.out.println(lRUCache.get(4));    // return 4
    lRUCache.put(4, 4);
    lRUCache.put(4, 5);
    lRUCache.put(4, 6);
    lRUCache.put(4, 6);
    lRUCache.put(4, 8);
    lRUCache.put(4, 9);
    lRUCache.put(4, 0);
    lRUCache.put(4, 7);
    lRUCache.put(5, 7);
    lRUCache.put(8, 7);
    System.out.println(lRUCache.get(4));
    System.out.println(lRUCache.get(8));
  }

}
