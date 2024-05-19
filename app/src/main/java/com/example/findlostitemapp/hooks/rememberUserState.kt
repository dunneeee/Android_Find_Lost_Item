package com.example.findlostitemapp.hooks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.findlostitemapp.api.UserServices
import com.example.findlostitemapp.domain.model.Http
import com.example.findlostitemapp.exceptions.ApiExceptions
import com.google.gson.Gson

data class UserCountState(
    val state: ApiState<Int>,
    val execute: () -> Unit
)

@Composable
fun rememberUserCountState(): UserCountState {
    val state = rememberApiState<Int>()
    val services = UserServices.getInstance(LocalContext.current)
    fun execute() {
        state.execute {
            try {
                val res = services.getUserCount()
                println(res.body())
                if (res.isSuccessful) {
                    val data = res.body()?.data ?: 0
                    data
                } else {
                    val error = Gson().fromJson<Http.Error<String>>(res.errorBody()?.string(), Http.Error::class.java)
                    when (error.code) {
                        else -> throw ApiExceptions.InternalServerError()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    return remember(state) {
        UserCountState(
            state = state,
            execute = ::execute
        )
    }
}