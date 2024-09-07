package com.example.interviews.codility

import org.junit.jupiter.api.Test
import java.util.*

class CodilityQuestionsLeaderTest {

    /**
     * https://app.codility.com/programmers/lessons/8-leader/equi_leader/
     */
    @Test
    //total 55%. 100% correctness 0% performance
    fun test_equiLeader() {
        fun solutionFirstTry(A: IntArray): Int {

            var equiLeaders = 0

            fun checkIfLeader(candidate: Int, size: Int): Boolean {
                var counter = 0
                for (i in 0 until size) {
                    if (A[i] == candidate) {
                        counter++
                    }
                }
                return counter > size / 2
            }

            fun checkIfLeaderInOtherPartition(num: Int, start: Int): Boolean {
                var counter = 0
                for (i in start until A.size) {
                    if (A[i] == num) {
                        counter++
                    }
                }
                return counter > (A.size - start) / 2
            }

            if (A.size <= 1) {
                return 0
            }

            val stack = Stack<Int>()
            for (i in 0 until A.size - 1) {
                if (stack.isEmpty()) {
                    stack.push(A[i])
                } else {
                    if (stack.peek() != A[i]) {
                        stack.pop()
                    } else {
                        stack.push(A[i])
                    }
                }

                if (stack.isNotEmpty()) {
                    if (checkIfLeader(stack.peek(), i + 1)) {
                        if (checkIfLeaderInOtherPartition(stack.peek(), i + 1)) {
                            equiLeaders++
                        }
                    }
                }
            }

            return equiLeaders
        }

        //this solution got 100% correctness and performance
        fun solution(A: IntArray): Int {
            fun findLeader(): Int? {
                val stack = Stack<Int>()
                for (i in A.indices) {
                    if (stack.isEmpty()) {
                        stack.push(A[i])
                    } else {
                        if (stack.peek() != A[i]) {
                            stack.pop()
                        } else {
                            stack.push(A[i])
                        }
                    }
                }
                if (stack.isNotEmpty()) {
                    val candidate = stack.pop()
                    var counter = 0
                    for (element in A) {
                        if (candidate == element) {
                            counter++
                        }
                    }
                    return if (counter > A.size / 2) {
                        candidate
                    } else {
                        null
                    }
                }
                return null
            }

            val findLeader = findLeader()
            if (findLeader == null) {
                return 0
            } else {
                var equiLeaders = 0
                var leftPartition = 1
                var rightPartition = A.size - 1
                var leaderInLeftPartition = 0
                var leaderInRightPartition = 0
                if (A[0] == findLeader) {
                    leaderInLeftPartition++
                }

                for (i in 1 until A.size) {
                    if (A[i] == findLeader) {
                        leaderInRightPartition++
                    }
                }

                for (i in 0 until A.size - 1) {
                    if ((leaderInLeftPartition > leftPartition / 2) &&
                            (leaderInRightPartition > rightPartition / 2)) {
                        equiLeaders++
                    }

                    if (A[i + 1] == findLeader) {
                        leaderInLeftPartition++
                        leaderInRightPartition--
                    }

                    rightPartition--
                    leftPartition++
                }

                return equiLeaders


            }


        }

        println(solution(listOf(4,3,4,4,4,2).toIntArray()))
    }

}