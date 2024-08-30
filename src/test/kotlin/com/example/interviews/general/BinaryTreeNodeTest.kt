package com.example.interviews.general

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BinaryTreeNodeTest {

    @Test
    fun test_inOrderPrinting() {
        val tree = BinaryTreeNode(1,
                left = BinaryTreeNode(2,
                        left = BinaryTreeNode(4),
                        right = BinaryTreeNode(5)
                        ),
                right = BinaryTreeNode(3,
                        right = BinaryTreeNode(6)
                        )
                )

        assertThat(printInOrder(tree)).containsExactly(4,2,5,1,3,6)
    }

}