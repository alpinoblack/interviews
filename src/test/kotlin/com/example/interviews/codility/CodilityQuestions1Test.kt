package com.example.interviews.codility

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.math.abs
import kotlin.random.Random

class CodilityQuestions1Test {

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

    @Test //MaxCounters
    //This is a medium difficulty question
    //Got 100% in correctness and performance
    fun test_maxCounters() {
        fun solution(N: Int, A: IntArray): IntArray {
            var currentMax = 0;
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

    @Test
    //Got 100% in performance and correctness
    fun test_Brackets() {
        fun solution(S: String): Int {
            val openingParentheses = ArrayDeque<Char>() //Java says this is faster than Stack<T>
            for (char in S) {
                if ("{[(".contains(char)) {
                    openingParentheses.push(char)
                } else {
                    if (openingParentheses.isEmpty()) {
                        return 0
                    }
                    val openParentheses = openingParentheses.pop()
                    when(char) {
                        '}' -> if (openParentheses != '{') return 0
                        ')' -> if (openParentheses != '(') return 0
                        ']' -> if (openParentheses != '[') return 0
                    }
                }
            }
            return if (openingParentheses.isEmpty()) 1 else 0
        }

        println(solution("({})[]["))
    }

}