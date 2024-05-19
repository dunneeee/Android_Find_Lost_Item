package com.example.findlostitemapp.domain.model

import com.example.findlostitemapp.interfaces.ModelConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File

object User {
    data class Instance(
        val uuid: String,
        val username: String,
        val email: String,
        val avatar: String,
        val isAdmin: Boolean
    ) : ModelConverter {
        override fun toJson(): String {
            val gson = Gson()
            return gson.toJson(this)
        }

    }

    data class WithPassword(
        val uuid: String,
        val username: String,
        val email: String,
        val password: String,
        val isAdmin: Boolean,
        val avatar: String
    )

    data class Login(
        val username: String,
        val password: String
    )

    data class Register(
        val username: String,
        val email: String,
        val password: String,
        val avatar: File
    )

    data class Create(
        val username: String,
        val email: String,
        val isAdmin: Boolean,
        val avatar: File,
        val password: String
    )
}

typealias UserPayload = Http.Payload<List<User.Instance>>

typealias SingleUserPayload = Http.Payload<User.Instance>