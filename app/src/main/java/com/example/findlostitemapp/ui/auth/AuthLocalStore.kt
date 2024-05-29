package com.example.findlostitemapp.ui.auth

import android.content.Context
import com.example.findlostitemapp.domain.model.User
import com.example.findlostitemapp.interfaces.ModelConverter
import java.util.Base64

class AuthLocalStore(context: Context) {
    companion object {
        private const val PREF_NAME = "auth"
        const val PREF_KEY_USER = "user"
        const val PREF_TOKEN = "token"
    }

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getToken(): String? {
        return sharedPreferences.getString(PREF_TOKEN, null)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(PREF_TOKEN, token).apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    fun saveUser(user: User.Instance) {
        val json = user.toJson()
        sharedPreferences.edit().putString(PREF_KEY_USER, json).apply()
    }

    fun getUser(): User.Instance? {
        val json = sharedPreferences.getString(PREF_KEY_USER, null) ?: return null
        return ModelConverter.fromJson(json, User.Instance::class.java)
    }

    fun decodedToken(): String? {
        val basicToken = getToken() ?: return null
        return String(Base64.getDecoder().decode(basicToken))
    }

    fun encodedToken(): String? {
        val basicToken = getToken() ?: return null
        return Base64.getEncoder().encodeToString(basicToken.toByteArray())
    }

    fun logout() {
        clear()
    }
}