package com.example.findlostitemapp.interfaces

import com.google.gson.Gson

interface ModelConverter {
    fun toJson(): String

    companion object {
        fun <T> fromJson(json: String, clazz: Class<T>): T {
            val gson = Gson()
            return gson.fromJson(json, clazz)
        }
    }
}