package com.example.interviews.general

/**
 * Implement an SQL-like group-by with aggregations
 * Given an arbitrary collection of entries of random size
 *
 * Example -
 * Given table peoples
 * last_name    , first_name , age, grade
 * family_name_1, first_name1, 12 , 80
 * family_name_2, first_name2, 21 , 81
 * family_name_1, first_name1, 20 , 80
 * family_name_2, first_name2, 13 , 92
 *
 * support the following query
 * select last_name, first_name, sum(age) from peoples order by last_name,first_name
 *
 * For simplicity assume only "sum" is available as an aggregation
 * Assume the input is correct and valid, all the data is given as strings
 */

fun myGroupBy(input: List<List<String>>, header: List<String>
              , aggs: List<String>, groupBys: List<String>): List<List<String>> {

    fun translateFieldsToIndexes(fields: List<String>): List<Int> {
        return header.withIndex()
                .filter { (_, headerElement) -> fields.contains(headerElement) }
                .map { it.index }
    }

    val aggIndexes = translateFieldsToIndexes(aggs)
    val groupByIndexes = translateFieldsToIndexes(groupBys)

    val aggregationMap = mutableMapOf<List<String>, List<Int>>()

    for (entry in input) {
        val entryKey = mutableListOf<String>()
        for (groupByIndex in groupByIndexes) {
            entryKey.add(entry[groupByIndex])
        }

        val selectAggsFromEntry = mutableListOf<Int>()
        for (aggIndex in aggIndexes) {
            selectAggsFromEntry.add(entry[aggIndex].toInt())
        }

        aggregationMap.merge(entryKey, selectAggsFromEntry.toList()) {
            list1, list2 -> list1.zip(list2).map { it.first + it.second }
        }
    }

    return aggregationMap.map { (fields, aggs) ->
        fields + aggs.map { it.toString() }
    }

}

fun main(args: Array<String>) {
    val inputs = listOf(
            listOf("family_name_1", "first_name1", "12" , "80"),
            listOf("family_name_2", "first_name2", "21" , "81"),
            listOf("family_name_1", "first_name1", "20" , "80"),
            listOf("family_name_2", "first_name2", "13" , "92"),
            listOf("family_name_1", "first_name1", "13" , "92")
    )

    val header = listOf("last_name", "first_name" , "age", "grade")

    val aggs = listOf("age")

    val groupBys = listOf("first_name", "last_name")

    val myGroupBy = myGroupBy(inputs, header, aggs, groupBys)

    println(myGroupBy)
}

