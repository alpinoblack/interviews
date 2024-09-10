package com.example.interviews.general.project1;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class MinMergeIterator<T extends Comparable<T>> implements Iterator<T> {

  private final List<PeekingIterator<T>> peekingIterators;
  private final PriorityQueue<PeekingIterator<T>> queue;

  public MinMergeIterator(final List<Iterator<T>> iterators) {
    this.peekingIterators = iterators.stream()
        .map(PeekingIterator::new).toList();
    this.queue = new PriorityQueue<>((iterator1, iterator2) -> {

      return iterator1.peek().compareTo(iterator2.peek());
    }
    );
    queue.addAll(peekingIterators);
  }

  @Override
  public boolean hasNext() {
    return peekingIterators.stream().anyMatch(PeekingIterator::hasNext);
  }

  @Override
  public T next() {
    return queue.peek().next();
  }

  public static void main(String[] args) {
    final var integerMinMergeIterator = new MinMergeIterator<>(List.of(
        List.of(1, 2, 3).iterator()/*,
        List.of(1, 4, 5).iterator()*/
    ));

    integerMinMergeIterator.forEachRemaining(integer -> System.out.println(integer));
  }
}
