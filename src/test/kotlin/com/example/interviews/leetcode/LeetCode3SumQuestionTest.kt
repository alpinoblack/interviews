package com.example.interviews.leetcode

import org.junit.jupiter.api.Test
import kotlin.math.abs


/**
 * https://leetcode.com/problems/3sum/
 */
class LeetCode3SumQuestionTest {

    /**
     * https://leetcode.com/problems/3sum/
     */
    @Test
    //correct solution got abysmal scores in performance and memory
    //sorting helps a little
    fun test_3Sum_1st_attempt() {
        fun threeSum(nums: IntArray): List<List<Int>> {

            fun twoSumPairs(nums: IntArray, k: Int,
                            start: Int, end: Int): List<Pair<Int, Int>> {
                val twoSumPairs = mutableListOf<Pair<Int, Int>>()
                val twoSumMap = mutableMapOf<Int, Int>()
                for (i in start..end) {
                    if (!twoSumMap.containsKey(nums[i])) {
                        twoSumMap[k - nums[i]] = i
                    } else {
                        twoSumPairs.add(twoSumMap[nums[i]]!! to i)
                    }
                }
                return twoSumPairs
            }

            nums.sort() //sorting in-place
            val threeSumTripletsSets = mutableSetOf<List<Int>>()
            for (i in nums.indices) {
                val threeSum1st = nums[i]
                val twoSumSolution = twoSumPairs(nums, 0 - threeSum1st, i + 1, nums.size - 1)
                twoSumSolution.forEach { twoSumPair ->
                    threeSumTripletsSets.add(
                            listOf(threeSum1st,
                                    nums[twoSumPair.first],
                                    nums[twoSumPair.second]).sorted())
                }
            }

            return threeSumTripletsSets.toList()
        }

        var result = threeSum(listOf(-1,0,1,2,-1,-4,-2,-3,3,0,4).toIntArray())
        var result2 = threeSum(listOf(-1,0,1,2,-1,-4).toIntArray())
        println(result)
        println(result2)
    }

}