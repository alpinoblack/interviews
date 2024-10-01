package com.example.interviews.general

/**
 * The following questions were all given in multiple interviews
 * of a single company.
 */


/**
 * Given a list of strings, return a list of matching string lengths
 */
fun question1(words: List<String>): List<Int> {
    return words.map { it.length }
}

/**
 * Given a list integers, sort the list by number of occurrences (desc) and do
 * a secondary sort according to the appearance in the initial list. Example:
 * 5,2,1,2,3,4 -> 2,2,5,1,3,4
 */
fun question2(nums: List<Int>): List<Int> {
    val occAndIndexMap: Map<Int, Pair<Int, Int>> = nums.foldIndexed(mutableMapOf()) { index, accMap, num ->
        if (!accMap.containsKey(num)) {
            accMap[num] = Pair(1, index)
        } else {
            accMap.computeIfPresent(num) { _, occurrencesOriginalIndexPair ->
                Pair(occurrencesOriginalIndexPair.first + 1, index)
            }
        }
        accMap
    }

    class MyComparator : Comparator<Pair<Int, Pair<Int, Int>>> {
        override fun compare(o1: Pair<Int, Pair<Int, Int>>, o2: Pair<Int, Pair<Int, Int>>): Int {
            val comparingOccurrences = o1.second.first.compareTo(o2.second.first)
            return if (comparingOccurrences != 0) {
                comparingOccurrences * -1 //descending for occurrences but not for the original ordering
            } else {
                o1.second.second.compareTo(o2.second.second)
            }

        }

    }

    return occAndIndexMap.map { Pair(it.key, it.value) }.toList()
            .sortedWith(MyComparator())
            .flatMap { numAndOccurrences ->
                val originalNumber = numAndOccurrences.first
                val occurrences = numAndOccurrences.second.first
                List(occurrences) { originalNumber }
            }
}

/**
 * Follow-up question - How can we parallelize our algorithm for sorting more efficiently?
 * Answer - We can create the occurrences index map in a single thread
 * so 5,2,1,2,3,4 turns into (5, (1,0)), (2, (2,1)), (1, (1,2)), (3, (1,4)), (4, (1,5))
 * then we can divide the resulting list into groups which has the same number of occurrences
 * so (2, (2,1)) will be sent to thread #1 and (5, (1,0)), (1, (1,2)), (3, (1,4)), (4, (1,5))
 * will be sent to thread #2 in this case. Now each thread can sort its group and when the threads
 * will return the intermediate results all we have to do now is to merge sorted arrays
 */

/**
 * write a function which takes a list of integers
 * and returns the sum of even and odd numbers in a single pass
 */
fun question3(numbers: List<Int>): Pair<Int, Int> {
    return numbers.fold(Pair(0, 0)) { acc, num ->
        if (num % 2 == 0) {
            Pair(acc.first + num, acc.second)
        } else {
            Pair(acc.first, acc.second + num)
        }
    }
}

/**
 * Design a parking lot building. The parking lot has
 * multiple floors and each floor has multiple spots. There are different types
 * of vehicles which require different types of parking spots. There can be for instance
 * a private 4 wheel Sedan, a 2 wheel motorcycle, a truck and so forth.
 * The parking lot also offer specialized parking spots for handicapped people and electrical vehicle
 * parking spots with chargers. Any of the types of vehicle can have any combination of the specialized vehicle
 * parking spots.
 */



fun main() {
    println(question2(listOf(5,2,1,2,3,4,6,7,6)))
}

/**
 * Design an elevator system
 * todo
 */
