package com.example.interviews.general;

import java.util.Stack;

/**
 * <a href="https://leetcode.com/problems/min-stack/description/">...</a>
 * For the sake of simplicity LeetCode only requires to implement for simple int
 */
// correct solution, in performance beats 80%, in memory beats 50%
// This changes rapidly with multiple submissions
class LeetCodeMinStack {
  private final Stack<Integer> innerStack;
  private final Stack<Integer> auxStack;

  public LeetCodeMinStack() {
    this.innerStack = new Stack<>();
    this.auxStack = new Stack<>();
  }

  public void push(int val) {
    innerStack.push(val);
    if (auxStack.isEmpty() || auxStack.peek() >= val) {
      auxStack.push(val);
    }
  }

  public void pop() {
    final int reqElement = innerStack.pop();
    if (reqElement == auxStack.peek()) {
      auxStack.pop();
    }
  }

  public int top() {
    return this.innerStack.peek();
  }

  public int getMin() {
    return this.auxStack.peek();
  }
}
