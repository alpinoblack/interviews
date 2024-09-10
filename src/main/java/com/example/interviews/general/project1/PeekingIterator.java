package com.example.interviews.general.project1;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class PeekingIterator<T> implements Iterator<T> {

  private final Iterator<T> backingIterator;
  private T peekValue = null;

  public PeekingIterator(Iterator<T> backingIterator) {
    this.backingIterator = backingIterator;
  }

  @Override
  public boolean hasNext() {
    if (peekValue != null) {
      return true;
    }

    return backingIterator.hasNext();
  }

  @Override
  public T next() {
    if (peekValue != null) {
      final T oldPeekValue = peekValue;
      if (backingIterator.hasNext()) {
        peekValue = backingIterator.next();
      } else  {
        peekValue = null;
      }
      return oldPeekValue;
    } else {
      if (backingIterator.hasNext()) {
        final T returnedValue = backingIterator.next();
        if (backingIterator.hasNext()) {
          peekValue = backingIterator.next();
        }
        return returnedValue;
      }
      throw new NoSuchElementException();
    }
  }

  public T peek() {
    if (peekValue != null) {
      return peekValue;
    }

    if (backingIterator.hasNext()) {
      peekValue = backingIterator.next();
      return peekValue;
    }

    throw new NoSuchElementException();
  }

  public static void main(String[] args) {
    final List<Integer> list = List.of(1,2,3,4,5);
    final Iterator<Integer> listIter = list.iterator();
    final var peekingItertor = new PeekingIterator<>(listIter);
    System.out.println(peekingItertor.peek());
    System.out.println(peekingItertor.peek());
    System.out.println(peekingItertor.next());
    System.out.println(peekingItertor.peek());
    peekingItertor.forEachRemaining(elem -> {
      System.out.println("value " + elem);
  //    System.out.println("peek value " + peekingItertor.peek());
    });
  }
}
