package com.example.interviews.codility

import org.junit.jupiter.api.Test

class CodilitQuestionsArraysTest {

    /**
     * https://app.codility.com/programmers/lessons/2-arrays/cyclic_rotation/
     */
    @Test //CyclicRotation
    //simple solution
    //Got 100% correctness
    fun test_cyclicRotation() {
        fun solution(A: IntArray, K: Int): IntArray {
            if (K == 0 || A.isEmpty()) {
                return A
            }
            val effectiveK = K % A.size
            if (effectiveK == 0) {
                return A
            }

            val duplicateArr = A.copyOf(A.size * 2)
            for (i in A.indices) {
                duplicateArr[i + A.size] = A[i]
            }

            val startOfNewArr = A.size * 2 - effectiveK - A.size

            for (i in A.indices) {
                A[i] = duplicateArr[i + startOfNewArr]
            }

            return A
        }

        println(solution(listOf(3, 8, 9, 7, 6).toIntArray(), 3))

    }

    /**
     * https://app.codility.com/programmers/lessons/2-arrays/odd_occurrences_in_array/
     */
    @Test //OddOccurrencesInArray
    //Got 100% in both correctness and performance
    fun test_oddOccurrencesInArray() {
        fun solution(A: IntArray): Int {
            var oddNumber = 0
            for (element in A) {
                oddNumber = oddNumber xor element
            }
            return oddNumber
        }
    }

}