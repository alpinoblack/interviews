package com.example.interviews.codility

import org.junit.jupiter.api.Test
import kotlin.math.abs

class CodilityQuestionsTimeComplexityTest {

    /**
     * https://app.codility.com/programmers/lessons/3-time_complexity/frog_jmp/
     */
    @Test //FrogJmp
    //Got 100% in correctness and performance
    fun test_frogJmp() {
        fun solution(X: Int, Y: Int, D: Int): Int {
            val distance = Y - X
            var jumps = distance / D
            if (distance % D != 0) {
                return ++jumps
            }
            return jumps
        }
    }

    /**
     * https://app.codility.com/programmers/lessons/3-time_complexity/perm_missing_elem/
     */
    @Test //PermMissingElem
    //Got 100% in correctness and performance
    fun test_permMissingElem() {
        fun solution(A: IntArray): Int {
            var intendedSumArray: Long = ((A.size + 1L + 1L) * (A.size + 1L))/2
            for (element in A) {
                intendedSumArray -= element
            }
            return intendedSumArray.toInt()
        }
    }

    /**
     * https://app.codility.com/programmers/lessons/3-time_complexity/tape_equilibrium/
     */
    @Test //TapeEquilibrium
    //Got 100% correctness and performance
    fun test_tapeEquilibrium() {
        fun solution(A: IntArray): Int {
            var leftPartSum = A[0]
            var rightPartSum = 0

            for (i in 1 until A.size ) {
                rightPartSum += A[i]
            }

            var minimalDifference = abs(rightPartSum - leftPartSum)

            for (i in 1 until A.size - 1) {
                leftPartSum += A[i]
                rightPartSum -= A[i]
                val currentDifference = abs(rightPartSum - leftPartSum)
                if (currentDifference < minimalDifference) {
                    minimalDifference = currentDifference
                }
            }
            return minimalDifference
        }

        println(solution(listOf(3, 1, 2, 4, 3).toIntArray()))
    }

}