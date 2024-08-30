package com.example.interviews.leetcode

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class LeetCode1Test {

    @Test
    fun test_removeDuplicates() {
        fun removeDuplicates(nums: IntArray): Int {
            var currentNum = nums[0]
            var lastDuplicateIndex = 1
            var uniqueElements = 1
            for (i in nums.indices.drop(1)) {
                if (nums[i] != currentNum) {
                    nums[lastDuplicateIndex] = nums[i]
                    lastDuplicateIndex++
                    uniqueElements++
                    currentNum = nums[i]
                }
            }
            return uniqueElements
        }

        val nums = listOf(0, 0, 1, 1, 1, 2, 2, 3, 3, 4).toIntArray()
        println(removeDuplicates(nums))
        println(nums.toList())
    }

    @Test
    fun test_removeDuplicates2() {
        fun removeDuplicates(nums: IntArray): Int {
            var currentNum = nums[0]
            var lastDuplicateIndex = 1
            var uniqueElements = 1
            var i = 1
            while (i < nums.size) {

                if (nums[i] != currentNum) {
                    nums[lastDuplicateIndex] = nums[i]
                    lastDuplicateIndex++
                    uniqueElements++
                    currentNum = nums[i]
                }
                i++
            }

            return uniqueElements
        }

        val nums = listOf(0, 0, 1, 1, 1, 2, 2, 3, 3, 4).toIntArray()
        println(removeDuplicates(nums))
        println(nums.toList())
    }

    /*@Test
    fun testArrayPermutation() {
        fun buildArray(nums: IntArray): IntArray {
            nums.copyOf()
        }
    }   */

    @Test //Concatenation of Array
    fun test_arrayConcatantion() {
        fun getConcatenation(nums: IntArray): IntArray {
            val newNums = IntArray(nums.size * 2)
            for (i in nums.indices) {
                newNums[i] = nums[i]
                newNums[i + nums.size] = nums[i]
            }
            return newNums
        }
        println(getConcatenation(listOf(1, 2, 1).toIntArray()).toList())
    }

    @Test //Find Minimum Operations to Make All Elements Divisible by Three
    fun test_minimumOperations() {
        fun minimumOperations(nums: IntArray): Int {
            var operations = 0
            for (i in nums.indices) {
                if (nums[i] % 3 != 0) {
                    operations++
                }
            }
            return operations
        }

        println(minimumOperations(listOf(1,2,3,4).toIntArray()))
    }

    @Test //Number of Good Pairs
    fun test_numIdenticalPairs() {
        fun numIdenticalPairs(nums: IntArray): Int {
            var goodPairs = 0
            for (i in nums.indices) {
                for (j in i + 1 until nums.size) {
                    if (nums[i] == nums[j]) {
                        goodPairs++
                    }
                }
            }
            return goodPairs
        }
        println(numIdenticalPairs(listOf(1).toIntArray()))
    }

    @Test //Largest Local Values in a Matrix
    fun test_largestLocal() {

        fun findMax(grid: Array<IntArray>, x: Int, y: Int): Int {
            var max = 0
            for (i in x .. x + 2) {
                for (j in y .. y + 2) {
                    if (max < grid[i][j]) {
                        max = grid[i][j]
                    }
                }
            }
            return max
        }

        fun largestLocal(grid: Array<IntArray>): Array<IntArray> {
            val newArray = Array(grid.size - 2) { IntArray(grid[0].size - 2) }

            for (i in newArray.indices) {
                for (j in 0 until newArray[i].size) {
                    newArray[i][j] = findMax(grid, i, j)
                }
            }

            return newArray
        }

        val message = largestLocal(listOf(
                listOf(1,1,1,1,1).toIntArray(),
                listOf(1,1,1,1,1).toIntArray(),
                listOf(1,1,2,1,1).toIntArray(),
                listOf(1,1,1,1,1).toIntArray(),
                listOf(1,1,1,1,1).toIntArray(),
        ).toTypedArray()
        )

        println(message)
    }

    @Test //Remove One Element to Make the Array Strictly Increasing
    fun test_canBeIncreasing() {
        fun canBeIncreasing(nums: IntArray): Boolean {
            var removed = false
            for (i in 1 until nums.size) {
                if (nums[i - 1] >= nums[i]) {
                    if (removed) {
                        return false
                    } else {
                        if (i > 1) {
                            if (nums[i] <= nums[i - 2]) {
                                nums[i] = nums[i - 1]
                            }
                        }
                        removed = true
                    }
                }
            }
            return true
        }

        println(canBeIncreasing(listOf(105,924,32,968).toIntArray()))
    }

    @Test
    fun test_canPlaceFlowers() { //Can Place Flowers
        fun canPlaceFlowers(flowerbed: IntArray, n: Int): Boolean {
            var countZeroes = 0
            for (i in flowerbed.indices) {
                if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0) && (i == (flowerbed.size - 1) || flowerbed[i + 1] == 0)) {
                    countZeroes++
                    flowerbed[i] = 1
                }
            }
            return countZeroes >= n
        }
    }

    @Test //Find The Original Array of Prefix Xor
    fun test_findArray() {
        fun findArray(pref: IntArray): IntArray {
            val arr = IntArray(pref.size)
            arr[0] = pref[0]
            for (i in 1 until pref.size) {
                arr[i] = pref[i] xor pref[i - 1]
            }
            return arr
        }

        println(findArray(listOf(5,2,0,3,1).toIntArray()).toList())
    }

    @Test
    fun test_groupThePeople() { //Group the People Given the Group Size They Belong To
        fun groupThePeople(groupSizes: IntArray): List<List<Int>> {
            val groups = mutableListOf<List<Int>>()
            val groupsMap: HashMap<Int, MutableSet<Int>> = HashMap()
            for (i in groupSizes.indices) {
                val groupSizeForPersonI = groupSizes[i]
                val group = groupsMap[groupSizeForPersonI] ?: mutableSetOf()
                group.add(i)
                if (group.size >= groupSizeForPersonI) {
                    groups.add(group.toList())
                    groupsMap.remove(groupSizeForPersonI)
                } else {
                    groupsMap[groupSizeForPersonI] = group
                }
            }
            return groups
        }

        groupThePeople(listOf(3,3,3,3,3,1,3).toIntArray())
    }

    @Test // Rotate Array Naive, not solved
    fun test_rotate() {

        fun switch(nums: IntArray, a: Int, b: Int) {
            val tempA = nums[a]
            nums[a] = nums[b]
            nums[b] = tempA
        }

        fun rotate(nums: IntArray, k: Int) {
            val effectiveK = k % nums.size
            if (effectiveK == 0) {
                return
            }

            for (i in 0 until effectiveK) {
                switch(nums, i, (nums.size - effectiveK) + i)
            }

            if (effectiveK % 2 == 1) {
                for (i in effectiveK until nums.size - 1) {
                    switch(nums, i, i + 1)
                }
            }

        }

        val nums = listOf(1,2,3,4,5,6).toIntArray()
        rotate(nums, 1)

        println(nums.toList())
    }

    @Test // Jump Game II
    fun test_jump() { //working but not very efficient

        fun minSolution(solutions: Map<Int, Int>, startKey: Int, jumps: IntRange): Int? {
            var min: Int? = null
            for (i in jumps) {
                val reachableSolution = startKey + i
                if (solutions[reachableSolution] != null) {
                    if (min == null || min > solutions[reachableSolution]!!) {
                        min = solutions[reachableSolution]
                    }
                }
            }
            return min
        }

        fun jump(nums: IntArray): Int {
            val solutions = mutableMapOf<Int,Int>()
            solutions[nums.size - 1] = 0
            for (i in nums.size - 2 downTo 0) {
                if (nums[i] + i >= nums.size - 1) {
                    solutions[i] = 1
                } else {
                    if (nums[i] != 0) {
                        val jumps = 0 ..nums[i]
                        val minimalSolution = minSolution(solutions, i ,jumps)
                        if (minimalSolution != null) {
                            solutions[i] = 1 + minimalSolution
                        }
                    }
                }
            }
            return solutions[0]!!
        }

        println(jump(listOf(1,2,3).toIntArray()))
        println(jump(listOf(2,3,1).toIntArray()))
        println(jump(listOf(1,2,3,4).toIntArray()))
        println(jump(listOf(1,2,1,1,1).toIntArray()))
        println(jump(listOf(10,9,8,7,6,5,4,3,2,1,1,0).toIntArray()))

    }

    @Test //Add Two Numbers
    fun test_addTwoNumbers() { //working, consider refactoring
        class ListNode(var value: Int, next: ListNode? = null) {
            var next: ListNode? = next
        }

        fun reverseList(list: ListNode?): ListNode? {
            var cList = list
            var newNode: ListNode? = null
            var prevNode: ListNode? = null
            while (cList != null) {
                if (newNode != null) {
                    prevNode = newNode
                }
                newNode = ListNode(cList.value)
                newNode.next = prevNode
                cList = cList.next
            }
            return newNode
        }

        fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
            var sum: ListNode? = null
            var remainder = 0
            var numL1 = l1
            var numL2 = l2

            while(numL1 != null && numL2 != null) {

                var newVal = remainder + numL2.value + numL1.value
                if (newVal >= 10) {
                    remainder = newVal / 10
                    newVal %= 10
                } else {
                    remainder = 0
                }

                val newNode = ListNode(newVal)
                newNode.next = sum
                sum = newNode

                numL1 = numL1.next
                numL2 = numL2.next
            }

            while(numL1 != null) {
                var newVal = remainder + numL1.value
                if (newVal >= 10) {
                    remainder = newVal / 10
                    newVal %= 10
                }
                numL1 = numL1.next

                val newNode = ListNode(newVal)
                newNode.next = sum
                sum = newNode
            }

            while(numL2 != null) {
                var newVal = remainder + numL2.value
                if (newVal >= 10) {
                    remainder = newVal / 10
                    newVal %= 10
                }
                numL2 = numL2.next

                val newNode = ListNode(newVal)
                newNode.next = sum
                sum = newNode
            }

            if (remainder != 0) {
                val newNode = ListNode(remainder)
                newNode.next = sum
                sum = newNode
            }

            return reverseList(sum)
        }

        val n = addTwoNumbers(ListNode(2, ListNode(4, ListNode(3))),
                ListNode(5, ListNode(6, ListNode(4))))

        println(n)
    }


/*    @Test //Longest Substring Without Repeating Characters
    fun test_lengthOfLongestSubstring() {
        fun lengthOfLongestSubstring(s: String): Int {
            var seenChars = mutableSetOf<Char>()
            var maximumSubstring = StringBuilder("")
            var currentWindow = 0
            for (i in maximumSubstring.indices) {
                if (seenChars.contains(s[i])) {
                    seenChars = mutableSetOf()
                }
            }

        }
    }*/

    @Test //2966. Divide Array Into Arrays With Max Difference
    fun test_divideArray() {
        fun divideArray(nums: IntArray, k: Int): Array<IntArray> {
            val sortedNums = nums.sortedArray() //biggest numbers in the end
            val result = ArrayList<IntArray>()
            var i = 0
            while( i < sortedNums.size) {
                val num1 = sortedNums[i]
                val num2 = sortedNums[i + 1]
                val num3 = sortedNums[i + 2]
                if (abs(num1 - num2) <= k &&
                        abs(num2 - num3) <= k &&
                        abs(num3 - num1) <= k) {
                    result.add(listOf(num1, num2, num3).toIntArray())
                } else {
                    return ArrayList<IntArray>().toTypedArray()
                }
                i+=3
            }
            return result.toTypedArray()
        }

        val result = divideArray(listOf(2,4,2,2,5,2).toIntArray(), 2)
        println(result.toList().map { it.toList() })
    }

    /**
     * https://leetcode.com/problems/first-missing-positive/description/
     **/
    @Test //First Missing Positive
    fun test_firstMissingPositive() {
        fun firstMissingPositive(nums: IntArray): Int {
            return 0
        }
    }

    /**
     * https://leetcode.com/problems/kth-largest-element-in-an-array/description/
     */
    @Test //Kth Largest Element in an Array
    //performance: beats 59%
    //memory: beats 98%
    fun test_findKthLargest() {
        fun findKthLargest(nums: IntArray, k: Int): Int {
            val priorityQueue = PriorityQueue<Int>(k, Collections.reverseOrder())
            for (num in nums) {
                priorityQueue.add(num)
            }
            for (i in 1 until k) {
                priorityQueue.poll()
            }
            return priorityQueue.poll()
        }
        println(findKthLargest(listOf(3,2,1,5,6,4).toIntArray(), 2))
    }



}