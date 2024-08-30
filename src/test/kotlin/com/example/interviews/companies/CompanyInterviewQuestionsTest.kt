package com.example.interviews.companies

import org.junit.jupiter.api.Test
import java.util.BitSet

class CompanyInterviewQuestionsTest {

    /**
     * String can contain numbers or '+' or '*', expression must be valid
     */
    @Test
    fun test_evaluatePlusMult() {

    }

    /**
     * Given a string which contains only lowercase letters and spaces, count the
     * appearances of each character and print a summary
     */
    @Test
    fun test_countCharacters() {
        val characterAppearances = hashMapOf<Char, Int>()
        val book = "absaa abbb aaaaa a "
        for (char in book) {
            characterAppearances.computeIfPresent(char) { _, value -> value + 1}
            characterAppearances.computeIfAbsent(char) {1}
        }
        println(characterAppearances)
    }

    /**
     * assume each element is either 1 or 0
     * sort the array so that 0s are at the beginning following all the 1s
     */
    @Test
    fun test_sortBinaryArray() {
        fun sortBinaryArray(binaryArray: Array<Int>): Array<Int> {
            var zeroes = 0
            var ones = binaryArray.size - 1
            while (zeroes < ones) {

                while(binaryArray[zeroes] == 0 && zeroes < ones) {
                    zeroes++
                }

                while(binaryArray[ones] == 1 && ones > zeroes) {
                    ones--
                }

                if (zeroes < ones) {
                    binaryArray[zeroes] = 0
                    binaryArray[ones] = 1
                }
            }
            return binaryArray
        }

        println(sortBinaryArray(listOf(1,1,1,1,1,0,1,1,1).toTypedArray()).toList())
    }

}