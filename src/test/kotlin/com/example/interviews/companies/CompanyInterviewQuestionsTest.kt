package com.example.interviews.companies

import com.example.interviews.general.Node
import com.example.interviews.general.linkedListToList
import com.example.interviews.general.reverse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*
import kotlin.random.Random


class CompanyInterviewQuestionsTest {

    /**
     * String can contain numbers or '+' or '*', expression must be valid
     */
    @Test
    fun test_evaluatePlusMult() {
        fun evaluateArithmeticExpression(exp: String): Int {
            val pluStack = Stack<Int>()
            var multAgg: Int? = null
            var i = 0
            while(i < exp.length - 1) {

                if (exp[i] == '*') {
                    multAgg = exp[i - 1].digitToInt() * exp[i + 1].digitToInt()
                }

                if (exp[i + 1] == '+') {
                    if (multAgg == null) {
                        pluStack.push(exp[i].digitToInt())
                    } else {
                        pluStack.push(multAgg)
                        multAgg = null
                    }
                }
                i++
            }

            if (multAgg != null) {
                pluStack.push(multAgg)
            }

            var agg = 0
            while(pluStack.isNotEmpty()) {
                agg += pluStack.pop()
            }
            return agg
        }

        println(evaluateArithmeticExpression("1*3*3+1*8+9*7"))
        println(evaluateArithmeticExpression("1+3+3+1*8+9*7"))
        println(evaluateArithmeticExpression("1+3*3"))

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

    /**
     * given a linked list, remove the k-th value from the end and
     * return the modified list
     */
    @Test
    fun test_removeKthElementFromEndOfList() {

        fun <T> removeKthElementFromTheEnd(k: Int, list: Node<T>): Node<T> {
            var kCounter = k
            var kthProbe: Node<T>? = list

            while(kCounter > 0 && kthProbe != null) {
                kCounter--
                kthProbe = kthProbe.next
            }

            if (kthProbe == null) {
                return list //less than k elements, do nothing
            }

            var currNode: Node<T>? = list
            var prevNode: Node<T>? = null
            var newList: Node<T>? = currNode

            if (kthProbe.next == null) {
                return list.next!! //in case we are at the head
            }

            while(kthProbe != null) {
                prevNode = currNode
                currNode = currNode?.next
                kthProbe = kthProbe.next
            }

            prevNode?.next = currNode?.next

            return newList!!
        }

        val testList = Node(1,
                Node(2,
                        Node(4,
                                Node(5)
                                )
                        )
        )

        val newList = removeKthElementFromTheEnd(2, testList)
        assertThat(linkedListToList(newList)).containsExactly(1,2,5)

        //val sameList = removeKthElementFromTheEnd(7, testList)
       // val sameListWithoutFirstElement = removeKthElementFromTheEnd(3, testList)

    }

    @Test
    fun test_reverseLinkedList() {

        val testList = Node(1,
                Node(2,
                        Node(4,
                                Node(5)
                        )
                )
        )

        assertThat(linkedListToList(reverse(testList))).containsExactly(5,4,2,1)
    }

    /**
     * You have a list of dates and a history of employment where the date
     * is the date where the person started in this position. The history is guaranteed to
     * be sorted.
     * Write a function which returns a map where the entry is each of the given dates
     * and the value is the correct role/position at the time
     */
    @Test
    fun test_connectDateAndRoles() {
        data class Role(val role: String, val effectiveFrom: LocalDate)
        val unknownRole = Role("N/A", LocalDate.of(1970, 1, 1))

        val dates = listOf(
                LocalDate.of(2014, 1, 2),
                LocalDate.of(2021, 2, 2),
                LocalDate.of(2016, 5, 7),
        )

        val employmentHistory = listOf(
                Role("Team Leader", LocalDate.of(2018, 1, 1)),
                Role("Senior Fullstack Developer",
                        LocalDate.of(2015, 1, 1)),
                Role("Junior Developer",
                        LocalDate.of(2014, 2, 1)),
        )

        //this is what you need to do
        fun matchDatesWithCorrectPositions(dates: List<LocalDate>,
                                           history: List<Role>): Map<LocalDate, Role> {
            val historyMap = mutableMapOf<LocalDate, Role>()
            for (date in dates) {
                for (entry in history) {
                    if (entry.effectiveFrom.isBefore(date)) {
                        historyMap[date] = entry
                        break
                    }
                }
                if (!historyMap.containsKey(date)) {
                    historyMap[date] = unknownRole
                }
            }
            return historyMap
        }

        fun printHistory(history: Map<LocalDate, Role>) {
            history.forEach { (date, role) ->
                println("Date: $date, Role: ${role.role}")
            }
        }

        printHistory(matchDatesWithCorrectPositions(dates, employmentHistory))

        /**
         * Follow-up Question - How would one implement the same in an SQL query?
         * Answer - select role from roles where effective_from is before $date
         * order by effective_from desc limit 1
         */
    }

    /**
     * Given an input array, find all possible subsets and return all of them
     * in a new array which is a superset of the original input array
     */
    @Test //recursive solution
    fun test_findAllSubsets() {
        fun findAllSubsets(input: List<Int>): List<List<Int>> {
            if (input.isEmpty()) {
                return listOf(emptyList())
            }
            val subsetsOfListTail = findAllSubsets(input.drop(1))
            val lists = subsetsOfListTail.map { subset ->
                listOf(input.first()) + subset
            }
            return subsetsOfListTail + lists
        }
        val input = listOf(1,2,3)
        println(findAllSubsets(input))

    }

    /**
     * Write a function which receives an array and returns an array
     * with the same values but in different random order
     */


    @Test
    fun test_randomArray() {

        fun returnRandomArray(input: List<Int>): List<Int> {

            "sdfsdfsdf".withIndex().map { (index, value) ->index to value}.toMap()

            val randArr = ArrayList<Int>(input.size)
            val inputCpy = ArrayList<Int>(input)
            for (i in 0 until input.size - 1) {
                val currentRandomIndex = Random.nextInt(i, input.size)
                randArr.add(inputCpy[currentRandomIndex])
                //swap selected element
                if (currentRandomIndex != i) {
                    val temp = inputCpy[i]
                    inputCpy[i] = inputCpy[currentRandomIndex]
                    inputCpy[currentRandomIndex] = temp
                }
            }
            randArr.add(inputCpy.last())
            return randArr
        }

        val inputArr = listOf(1,2,3,4,5,6,7,8,9)
        repeat(10) {
            println("Iteration: $it")
            val returnedArr = returnRandomArray(inputArr)
            println("Array is: $returnedArr")
            assertThat(returnedArr)
                    .containsExactlyInAnyOrder(*inputArr.toTypedArray())
                    .isNotEqualTo(inputArr)
        }
    }

    /**
     * You are given a string representing text and a pivot index.
     * Write a function which reverses the words in the text according to the pivot
     * example:
     * reverseWordsByPivot("helloworld", 3) -> "loworldhel"
     */
    @Test
    fun test_reverseWordsByPivot() {
        fun reverseWordsByPivot(text: String, pivot: Int): String {
            val reversed = text.reversed()
            val leftOfPivot = reversed.substring(0, text.length - pivot).reversed()
            val rightOfPivot = reversed.substring(text.length - pivot, text.length).reversed()
            return leftOfPivot + rightOfPivot
        }

        fun reverseWordsByPivotOptimized(text: String, pivot: Int): String { //didn't change the signature

            fun reverseByIndexes(text: CharArray, startInclusive: Int, endExclusive: Int) {
                val midPoint = (startInclusive + endExclusive) / 2
                var counterIndex = 0
                for (i in startInclusive until midPoint) {
                    val temp = text[i]
                    text[i] = text[endExclusive - 1 - counterIndex]
                    text[endExclusive - 1 - counterIndex] = temp
                    counterIndex++
                }
            }

            val textCharArr = text.toCharArray()

            reverseByIndexes(textCharArr, 0, textCharArr.size)
            reverseByIndexes(textCharArr, 0, textCharArr.size - pivot)
            reverseByIndexes(textCharArr, textCharArr.size - pivot, textCharArr.size)
            return textCharArr.joinToString("")
        }

        assertThat(reverseWordsByPivot("helloworld", 3)).isEqualTo("loworldhel")
        assertThat(reverseWordsByPivotOptimized("helloworld", 3)).isEqualTo("loworldhel")

        /**
         * Can optimize the solution by changing the signature to receive a char[]
         * since in Java strings are immutables, even changing the string in place will result
         * in a new string being created.
         *
         * Can implement reverse in place and then doing the same reverse on both sub strings
         * Thus decreasing space complexity to O(1)
         * Time complexity is O(n/2) for reverse and there is one pivot so only 2 further reverses
         * O(n/2) + O(n/(2*1stPartitionLength)) + O(n/(2*2ndPartitionLength)) -> O(n)
         */
    }





}