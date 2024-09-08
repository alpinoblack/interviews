package com.example.interviews.codility

import org.junit.jupiter.api.Test
import kotlin.math.max

class CodilityQuestionsMaxSliceTest {

    @Test
    //got 80% correctness and 50% performance
    //incorrect
    fun test_maxProfit() {
        fun solution(A: IntArray): Int {
            if (A.size <= 1) {
                return 0
            }
            var maxProfit = 0
            var maxProfitEndingInI: Int
            var maxProfitForBuyInI = 0
            for (i in 1 until A.size) {
                maxProfitEndingInI = max(A[i] - A[maxProfitForBuyInI], 0)
                if (maxProfit < maxProfitEndingInI) {
                    maxProfit = maxProfitEndingInI
                } else {
                    if (A[i] - A[maxProfitForBuyInI] < 0) {
                        maxProfitForBuyInI++
                    }
                }
            }

            return maxProfit
        }

        println(solution(
                listOf(23171, 21011, 21123, 21366, 21013, 21367).toIntArray())
        )
    }

    @Test
    //not my solution
    //got 100% correctness and performance
    fun test_maxProfit2() {
        fun solution(A: IntArray): Int {
            if (A.size <= 1) {
                return 0
            }
            var maxProfit = 0
            var comulativeProfit = 0
            for (i in 1 until A.size) {
                val currentDayProfit = A[i] - A[i - 1]
                comulativeProfit += currentDayProfit
                if (comulativeProfit > 0) {
                    if (comulativeProfit > maxProfit) {
                        maxProfit = comulativeProfit
                    }
                } else {
                    comulativeProfit = 0
                }
            }

            return maxProfit
        }

        println(solution(
                listOf(23171, 21011, 21123, 21366, 21013, 21367).toIntArray())
        )
    }

}