package com.example.interviews.general;

import java.util.Stack;

/**
 * <a href="https://leetcode.com/problems/min-stack/description/">...</a>
 * a generic version of the LeetCode task
 */
public class MinStack<T extends Comparable<T>> {
  private final Stack<T> innerStack;
  private final Stack<T> auxStack;
  public MinStack() {
    this.innerStack = new Stack<>();
    this.auxStack = new Stack<>();
  }

  public void push(final T e) {
    innerStack.push(e);
    if (auxStack.isEmpty() || auxStack.peek().compareTo(e) > 0
        || auxStack.peek().compareTo(e) == 0) {
      auxStack.push(e);
    }
  }

  public T getMin() {
    return auxStack.peek();
  }

  public T pop() {
    final T reqElement = innerStack.pop();
    if (reqElement == auxStack.peek()) {
      auxStack.pop();
    }
    return reqElement;
  }


}
