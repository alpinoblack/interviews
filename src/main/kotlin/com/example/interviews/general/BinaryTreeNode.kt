package com.example.interviews.general


/**
 * Inorder(tree)
 *
 *  Traverse the left subtree, i.e., call Inorder(left->subtree)
 *  Visit the root.
 *  Traverse the right subtree, i.e., call Inorder(right->subtree)
 *
 * Preorder(tree)
 *
 *  Visit the root.
 *  Traverse the left subtree, i.e., call Preorder(left->subtree)
 *  Traverse the right subtree, i.e., call Preorder(right->subtree)
 *
 * Algorithm Postorder(tree)
 *
 *  Traverse the left subtree, i.e., call Postorder(left->subtree)
 *  Traverse the right subtree, i.e., call Postorder(right->subtree)
 *  Visit the root
 */
class BinaryTreeNode<T>(val value: T,
                    val left: BinaryTreeNode<T>? = null,
                    val right: BinaryTreeNode<T>? = null)

fun <T> printInOrder(root: BinaryTreeNode<T>): List<T> {
    val nodesValues = ArrayList<T>()
    fun printInOrderAux(root: BinaryTreeNode<T>) {
        root.left?.also { printInOrderAux(it) }
        nodesValues.add(root.value)
        root.right?.also { printInOrderAux(it) }
    }
    printInOrderAux(root)
    return nodesValues
}