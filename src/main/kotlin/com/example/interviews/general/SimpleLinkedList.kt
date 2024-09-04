package com.example.interviews.general

class Node<T>(val value: T, var next: Node<T>? = null)

fun <T> linkedListToList(head: Node<T>): List<T> {
    var curr: Node<T>? = head

    val aggregator = ArrayList<T>()

    while (curr != null) {
        aggregator.add(curr.value)
        curr = curr.next
    }

    return aggregator
}

fun <T> reverse(head: Node<T>): Node<T> {
    var curr: Node<T>? = head
    var newHead: Node<T>? = null
    while (curr != null) {
        val newNode = Node(curr.value, newHead)
        newHead = newNode
        curr = curr.next
    }
    return newHead!!
}