package com.example.interviews.general.project1;

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

      T peek1 = iterator1.peek();
      T peek2 = iterator2.peek();
      return peek1.compareTo(peek2);
    }
    );
    //queue.addAll(peekingIterators);
    peekingIterators.forEach(a -> queue.add(a));
  }

  @Override
  public boolean hasNext() {
    return peekingIterators.stream().anyMatch(PeekingIterator::hasNext);
  }

  @Override
  public T next() {
    final PeekingIterator<T> iteratorWithBiggestFirstValue = queue.peek();
    final var a = iteratorWithBiggestFirstValue.next();
    if (!iteratorWithBiggestFirstValue.hasNext()) {
      queue.remove();
    } else {
      queue.poll();
      queue.add(iteratorWithBiggestFirstValue);
    }
    return a;




/*    PeekingIterator<T> poll = queue.poll();
    var value = poll.next();
    queue.add(poll);
    return value;*/
  }

  public static void main(String[] args) {
    final var integerMinMergeIterator = new MinMergeIterator<>(List.of(
        List.of(1, 2, 3).iterator(),
        List.of(1, 4, 5).iterator()
    ));

    integerMinMergeIterator.forEachRemaining(integer -> System.out.println(integer));
  }
}
