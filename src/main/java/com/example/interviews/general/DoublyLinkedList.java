package com.example.interviews.general;

import java.util.ArrayList;
import java.util.List;

public class DoublyLinkedList<T> {
  private ListNode<T> head;
  private ListNode<T> tail;

  public ListNode<T> getHead() {
    return head;
  }

  public ListNode<T> getTail() {
    return tail;
  }

  public List<String> printList() {
    final List<String> values = new ArrayList<>();
    ListNode<T> curr = head;
    while (curr != null) {
      values.add(curr.value.toString());
      curr = curr.next;
    }
    return values;
  }

  public void removeElement(final ListNode<T> element) {
    var newNext = element.next;
    var newPrev = element.prev;
    newPrev.setNext(newNext);
    newNext.setPrev(newPrev);
  }

  public static class ListNode<T> {
    private T value;
    private ListNode<T> prev;
    private ListNode<T> next;
    public ListNode(final T value, final ListNode<T> prev, final ListNode<T> next) {
      this.value = value;
      this.prev = prev;
      this.next = next;
    }

    public T getValue() {
      return this.value;
    }

    void setPrev(final ListNode<T> prev) {
      this.prev = prev;
    }

    public ListNode<T> getNext() {
      return this.next;
    }

    void setNext(final ListNode<T> next) {
      this.next = next;
    }

  }

  public DoublyLinkedList(final List<T> values){
    ListNode<T> nextNode = null;
    for (final T value: values) {
      var newNode = new ListNode<>(value, null, null);
      if (this.tail == null) {
        this.tail = newNode;
      }
      if (nextNode != null) {
        newNode.setNext(nextNode);
        nextNode.setPrev(newNode);
      }
      nextNode = newNode;
    }
    this.head = nextNode;
  }


}
