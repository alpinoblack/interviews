package com.example.interviews.general;


import static org.assertj.core.api.Assertions.assertThat;

import com.example.interviews.general.DoublyLinkedList.ListNode;
import java.util.List;
import org.junit.jupiter.api.Test;

class DoublyLinkedListTest {

  @Test
  public void test_buildingList() { //1 is head and 5 is tail
    final var integerDoublyLinkedList = new DoublyLinkedList<>(List.of(1, 2, 3, 4, 5));
    assertThat(integerDoublyLinkedList.printList())
        .containsExactly("5", "4", "3", "2", "1");
  }

  @Test
  public void test_removeElement() {
    final var integerDoublyLinkedList = new DoublyLinkedList<>(List.of(1, 2, 3, 4, 5));
    final ListNode<Integer> thirdElement = integerDoublyLinkedList.getHead().getNext().getNext();
    assertThat(thirdElement.getValue()).isEqualTo(3);
    integerDoublyLinkedList.removeElement(thirdElement);
    assertThat(integerDoublyLinkedList.printList()).containsExactly("5", "4", "2", "1");
  }



}