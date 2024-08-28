package com.example.interviews.general

class LRUCache<K,V>(private val capacity: Int) {

    private val map: LinkedHashMap<K,V> = LinkedHashMap(capacity)
    fun get(key: K): V? {
        val value = map[key]
        if (value != null) {
            map.remove(key)
            map[key] = value
        }
        return value
    }

    fun put(key: K, value: V) {
        if (map.entries.size == capacity) {
            map.remove(map.firstEntry().key)
        }
        map[key] = value
    }

}