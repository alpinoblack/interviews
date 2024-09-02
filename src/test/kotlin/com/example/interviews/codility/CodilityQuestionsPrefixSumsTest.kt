package com.example.interviews.codility

import org.junit.jupiter.api.Test


class CodilityQuestionsPrefixSumsTest {

    /**
     * https://app.codility.com/programmers/lessons/5-prefix_sums/passing_cars/
     */
    @Test
    //got 100% in correctness and performance
    fun test_passingCars() {
        fun solution(A: IntArray): Int {
            val prefixSums = Array(A.size) {0}
            prefixSums[0] = 0
            for (i in 1 until A.size) {
                if (A[i] == 1) {
                    prefixSums[i] = prefixSums[i - 1] + 1
                } else {
                    prefixSums[i] = prefixSums[i - 1]
                }
            }

            var passingCars = 0

            for (i in A.indices) {
                if (A[i] == 0) {
                    val rightSum = prefixSums[A.size - 1]
                    val leftSum = if (i == 0) {
                        0
                    } else {
                        prefixSums[i - 1]
                    }

                    passingCars += rightSum - leftSum
                    if (passingCars > 1_000_000_000) {
                        return - 1
                    }
                }
            }
            return passingCars
        }

        println(solution(listOf(0,1,0,1,1).toIntArray()))
    }

    /**
     * https://app.codility.com/programmers/lessons/5-prefix_sums/count_div/
     * */
    // got 100% for performance and correctness
    @Test
    fun test_countDiv() {
        fun solution(A: Int, B: Int, K: Int): Int {

            val divisibleByK = B / K
            val divisibleByKSmallerThenA = if (A % K == 0) {
                A / K - 1
            } else {
                A / K
            }

            return divisibleByK - divisibleByKSmallerThenA
        }

        println(solution(6, 11, 2)) // 3
        println(solution(11, 345, 17)) // 20?
        println(solution(0, 0, 11)) //1
    }

    @Test
    fun test_countDiv_correctSolution_bad_performance() {
        fun solution(A: Int, B: Int, K: Int): Int {

            var divisibleByKSmallerThenA = 0
            var divisibleByKSmallerThenB = 0

            var i = 0

            while (i <= B) {

                if (i % K == 0) {
                    divisibleByKSmallerThenB++
                    if (i < A) {
                        divisibleByKSmallerThenA++
                    }
                }

                i+=K
            }


            return divisibleByKSmallerThenB - divisibleByKSmallerThenA
        }

     //   println(solution(6, 11, 2)) //
        println(solution(11, 345, 17)) // 20?
    }


}