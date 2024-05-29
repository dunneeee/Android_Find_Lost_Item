package com.example.findlostitemapp.domain.model

import com.example.findlostitemapp.interfaces.ModelConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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

        fun getRole() = if (isAdmin) "Admin" else "User"

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
        val isAdmin: Boolean = false,
        val avatar: File? = null
    ) {
        fun toMap(): Map<String, @JvmSuppressWildcards RequestBody> {
            val map = mutableMapOf<String, RequestBody>()
            map["username"] = username.toRequestBody()
            map["email"] = email.toRequestBody()
            map["password"] = password.toRequestBody()
            map["isAdmin"] = isAdmin.toString().toRequestBody()
            return map
        }

        fun getAvatar(): MultipartBody.Part? {
            val file = avatar ?: return null
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData("avatar", file.name, requestFile)
        }
    }

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