package com.example.interviews.leetcode

import org.junit.jupiter.api.Test
import kotlin.math.min

class LeetCode2Test {

    /**
     * https://leetcode.com/problems/reverse-words-in-a-string/description/
     */
    @Test
    //correct solution, sub-optimal
    fun test_reverseWords() {
        fun reverseWords(s: String): String {
            return s.trim().reversed()
                    .split(" ")
                    .filter { it.isNotBlank() }.joinToString(" ") { it.reversed() }
        }
        println(reverseWords("   the sky  is blue"))

        val message = "   s  s".split(" ")
        println(message)
    }

    /**
     * https://leetcode.com/problems/verifying-an-alien-dictionary/description/
     */
    @Test
    fun test_isAlienSorted() {
        fun isAlienSorted(words: Array<String>, order: String): Boolean {

            if (words.size <= 1) {
                return true
            }

            val alphabetOrder: Map<Char, Int> = order.withIndex()
                    .associate { (index, value) -> value to index }

           wordsLoop@ for (i in 0 until words.size - 1) {
                val firstWord = words[i]
                val secondWord = words[i + 1]

                for (j in 0 until min(secondWord.length, firstWord.length)) {
                    if (alphabetOrder[firstWord[j]]!! < alphabetOrder[secondWord[j]]!!) {
                        continue@wordsLoop
                    } else if (alphabetOrder[firstWord[j]]!! > alphabetOrder[secondWord[j]]!!) {
                        return false
                    }
                }

                if (firstWord.length > secondWord.length) {
                    return false
                }

            }
            return true

        }

        println(isAlienSorted(listOf("kuvp", "q").toTypedArray(), "ngxlkthsjuoqcpavbfdermiywz"))
        println(isAlienSorted(listOf("fxasxpc","dfbdrifhp","nwzgs","cmwqriv","ebulyfyve","miracx","sxckdwzv","dtijzluhts","wwbmnge","qmjwymmyox").toTypedArray(), "zkgwaverfimqxbnctdplsjyohu"))
    }

    /**
     * https://leetcode.com/problems/product-of-array-except-self/description/
     */
    @Test //
    fun test_productExceptSelf() {
        fun productExceptSelf(nums: IntArray): IntArray {
            //todo
            return listOf<Int>().toIntArray()
        }
        println(productExceptSelf(listOf(1,2,3,4).toIntArray()).toList())
    }

}