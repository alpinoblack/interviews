package com.example.interviews.codility

import org.junit.jupiter.api.Test

class CodilityQuestionsIterationsTest {

    /**
     * https://app.codility.com/programmers/lessons/1-iterations/binary_gap/
     */
    @Test //BinaryGap
    //Got 100% correctness
    fun test_binaryGap() {

        fun convertToBinaryString(decimal: Int): String {
            var remainderDecimal = decimal
            val binaryString = StringBuilder("")
            while(remainderDecimal > 0) {
                binaryString.append(remainderDecimal % 2)
                remainderDecimal/=2
            }
            return binaryString.toString().reversed()
        }

        fun solution(N: Int): Int {
            val binaryStrRepresentation = convertToBinaryString(N)
            var longestBinaryGap = 0
            var currentBinarySearch = 0
            var duringBinaryGapSearch = false
            for (i in binaryStrRepresentation.dropLast(1).indices) {
                if (!duringBinaryGapSearch && binaryStrRepresentation[i] == '1') {
                    duringBinaryGapSearch = true
                } else if (duringBinaryGapSearch && binaryStrRepresentation[i] == '0') {
                    currentBinarySearch++
                    if (binaryStrRepresentation[i + 1] == '1') {
                        duringBinaryGapSearch = false
                        if (currentBinarySearch > longestBinaryGap) {
                            longestBinaryGap = currentBinarySearch
                        }
                        currentBinarySearch = 0
                    }
                }
            }
            return longestBinaryGap
        }

        println(solution(1041))

    }

}