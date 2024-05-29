package com.example.findlostitemapp.domain.model

import okhttp3.RequestBody

data class SearchData(val item: String, val location: String, val topic: String) {
    fun toMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["item"] = item
        map["location"] = location
        map["topic"] = topic
        return map
    }
}
