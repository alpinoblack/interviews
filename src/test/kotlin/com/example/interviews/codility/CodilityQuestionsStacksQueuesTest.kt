package com.example.interviews.codility

import org.junit.jupiter.api.Test
import java.util.*

class CodilityQuestionsStacksQueuesTest {

    /**
     * https://app.codility.com/programmers/lessons/7-stacks_and_queues/brackets/
     */
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