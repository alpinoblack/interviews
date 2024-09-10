package com.example.interviews.general.project1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class PeekingIteratorNotBasedOnIterator<T> implements Iterator<T> {
  private final ArrayList<T> backingList;
  private int index = 0;
  public PeekingIteratorNotBasedOnIterator(final List<T> backingCollection) {
    if (backingCollection.isEmpty()) {
      throw new IllegalArgumentException("can't use an empty collection");
    }
    this.backingList = new ArrayList<>(backingCollection);
  }
  @Override
  public boolean hasNext() {
    return index < backingList.size() - 1;
  }

  @Override
  public T next() {
    try {
      return backingList.get(index++);
    } catch (IndexOutOfBoundsException e) {
      throw new NoSuchElementException();
    }
  }

  public T peek() {
    return backingList.get(index);
  }

  public static void main(String[] args) {

  }

}
