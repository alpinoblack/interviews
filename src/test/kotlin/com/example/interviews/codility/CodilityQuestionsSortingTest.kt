package com.example.interviews.codility

import org.junit.jupiter.api.Test

class CodilityQuestionsSortingTest {

    /**
     * https://app.codility.com/programmers/lessons/6-sorting/max_product_of_three/
     */
    @Test
    //got 100% in performance and correctness
    fun test_MaxProductOfThree() {
        fun solution(A: IntArray): Int {
            val sortedInput = A.sorted()
            val allPositives = sortedInput[A.size - 1] *
                    sortedInput[A.size - 2] *
                    sortedInput[A.size - 3]

            val positivesAndNegatives = sortedInput[0] * sortedInput[1] * sortedInput[A.size - 1]

            return if (positivesAndNegatives > allPositives) {
                positivesAndNegatives
            } else {
                allPositives
            }
        }

        println(solution(listOf(-3,1,2,-2,5,6).toIntArray()))
    }


    /**
     * https://app.codility.com/programmers/lessons/6-sorting/triangle/
     */
    @Test
    fun test_triangle() {
        fun solution(A: IntArray): Int {
            if (A.size < 3) { //can't have a triplet if there are less than 3 elements
                return 0
            }

            val sortedInput = A.sorted()
            for (i in 0 until A.size - 2) {
                val a = sortedInput[i].toLong()
                val b = sortedInput[i + 1].toLong()
                val c = sortedInput[i + 2].toLong()

                if (a + b > c && a + c > b && b + c > a) {
                    return 1
                }
            }

            return 0
        }

        println(solution(listOf(10, 2, 5, 1, 8, 20).toIntArray()))
    }



}