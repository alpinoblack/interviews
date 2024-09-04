package com.example.interviews.companies

import com.example.interviews.general.Node
import com.example.interviews.general.linkedListToList
import com.example.interviews.general.reverse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime

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



}