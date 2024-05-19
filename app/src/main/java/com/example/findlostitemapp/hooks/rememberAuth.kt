package com.example.findlostitemapp.hooks

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.findlostitemapp.api.AuthServices
import com.example.findlostitemapp.domain.model.Http
import com.example.findlostitemapp.domain.model.User
import com.example.findlostitemapp.exceptions.ApiExceptions
import com.example.findlostitemapp.exceptions.AuthExceptions
import com.google.gson.Gson


data class LoginState(
    val state: ApiState<User.Instance>,
    val token: String,
    val execute: (User.Login) -> Unit
)

data class RegisterState(
    val state: ApiState<User.Instance>,
    val execute: (User.Register) -> Unit
)

@Composable
fun rememberLoginState(): LoginState {
    val state = rememberApiState<User.Instance>()
    var token by remember { mutableStateOf("") }
    val context = LocalContext.current
    val services = AuthServices.getInstance(context)
    fun execute(payload: User.Login) {
        state.execute {
            try {
                val res = services.login(payload)
                if (res.isSuccessful) {
                    val user = res.body()?.data!!
                    token = "${payload.username}:${payload.password}"
                    user
                } else {
                    throw AuthExceptions.InvalidCredentials()
                }
            } catch (e: Exception) {
                if (e is AuthExceptions) throw e
                Log.e("LoginState", e.message.toString())
                throw ApiExceptions.InternalServerError()
            }
        }
    }

    return remember(token, state) {
        LoginState(
            state = state,
            token = token,
            execute = ::execute
        )
    }
}

@Composable
fun rememberRegisterState(): RegisterState {
    val state = rememberApiState<User.Instance>()
    val context = LocalContext.current
    val services = AuthServices.getInstance(context)
    fun execute(payload: User.Register) {
        state.execute {
            try {
                val res = services.register(payload)
                if (res.isSuccessful) {
                    res.body()?.data!!
                } else {
                    val error = Gson().fromJson<Http.Error<String>>(res.errorBody()?.string(), Http.Error::class.java)
                    when (error.code) {
                        400 -> throw AuthExceptions.MissingFields(error.error)
                        409 -> throw AuthExceptions.UserAlreadyExists()
                        else -> throw ApiExceptions.InternalServerError()
                    }
                }
            } catch (e: Exception) {
                Log.e("RegisterState", e.message.toString())
                throw if (e is AuthExceptions) e else ApiExceptions.InternalServerError()
            }
        }
    }

    return remember(state) {
        RegisterState(
            state = state,
            execute = ::execute
        )
    }
}