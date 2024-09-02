package com.example.interviews.codility

import org.junit.jupiter.api.Test
import java.util.*

class CodilityQuestionsCountingElementsTest1 {

    /**
     * https://app.codility.com/programmers/lessons/4-counting_elements/frog_river_one/
     */
    @Test //FrogRiverOne
    //Got 100% in correctness and performance
    fun test_frogRiverOne() {
        fun solution(X: Int, A: IntArray): Int {
            val leavesToJump = IntArray(X) { 1 }
            var leaves = X
            for (i in A.indices) {
                if (leavesToJump[A[i] - 1] != 0) {
                    leaves--
                    leavesToJump[A[i] - 1] = 0
                }
                if (leaves == 0) {
                    return i
                }
            }
            return -1
        }

        println(solution(5, listOf(1,3,1,4,2,3,5,4).toIntArray()))
    }

    /**
     * https://app.codility.com/programmers/lessons/4-counting_elements/perm_check/
     */
    @Test //PermCheck
    //Got 100% in correctness and performance
    fun test_permCheck() {
        fun counting(arr: IntArray): IntArray {
            val countArray = IntArray(arr.size)
            for (element in arr) {
                if (element <= countArray.size) {
                    countArray[element - 1] = countArray[element - 1] + 1
                }
            }
            return countArray
        }

        fun solution(A: IntArray): Int {
            val countArray = counting(A)
            for (element in countArray) {
                if (element != 1) {
                    return -0
                }
            }
            return 1
        }

        println(solution(listOf(4,1,3).toIntArray()))
    }

    /**
     * https://app.codility.com/programmers/lessons/4-counting_elements/max_counters/
     */
    @Test //MaxCounters
    //Got 100% in correctness and performance
    fun test_maxCounters() {
        fun solution(N: Int, A: IntArray): IntArray {
            var currentMax = 0
            var currentMaxForAllCounters = 0
            val countArray = IntArray(N) { 0 }
            for (element in A) {
                if (element == N + 1) { //max all around
                    currentMaxForAllCounters = currentMax
                } else {
                    val currentValue = countArray[element - 1]

                    if (currentValue < currentMaxForAllCounters) {
                        countArray[element - 1] = currentMaxForAllCounters
                    }

                    countArray[element - 1] = countArray[element - 1] + 1

                    val newValue = countArray[element - 1]

                    if (newValue > currentMax) {
                        currentMax = newValue
                    }
                }
            }

            for (i in countArray.indices) {
                if (countArray[i] < currentMaxForAllCounters) {
                    countArray[i] = currentMaxForAllCounters
                }
            }

            return countArray
        }

        println(solution(5, listOf(3,4,4,6,1,4,4).toIntArray()).toList())
        val linkedList = LinkedList<Int>()

        linkedList.removeAt(2)
    }

    /**
     * https://app.codility.com/programmers/lessons/4-counting_elements/missing_integer/
     */
    @Test
    //got 100% correctness and performance
    fun test_MissingInteger_withSort() { //todo can accomplish with linear time
        fun solution(A: IntArray): Int {
            val sortedArray = A.sortedArray()
            var currentPositive = 0
            for (e in sortedArray) {
                if (e > 0) {
                    if (e != currentPositive) {
                        if (e != currentPositive + 1) {
                            return currentPositive + 1
                        } else {
                            currentPositive++
                        }
                    }
                }
            }
            return currentPositive + 1
        }

        // println(solution(listOf(1, 2, 3).toIntArray()))
        println(solution(listOf(1, 3, 6, 4, 1, 2).toIntArray()))
        println(solution(listOf(-1, -3).toIntArray()))
    }

}