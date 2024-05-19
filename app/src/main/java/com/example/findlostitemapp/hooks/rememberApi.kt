package com.example.findlostitemapp.hooks

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class ApiStateType<out T> {
    object IDLE : ApiStateType<Nothing>()
    object LOADING : ApiStateType<Nothing>()
    data class SUCCESS<T>(val data: T) : ApiStateType<T>()
    data class ERROR(val throwable: Exception) : ApiStateType<Nothing>()
}

class ApiState<T>() {
    var type by mutableStateOf<ApiStateType<T>>(ApiStateType.IDLE)
        private set

    //    val type get() = _type
    val data: T?
        get() = (type as? ApiStateType.SUCCESS)?.data
    val error: Throwable? get() = (type as? ApiStateType.ERROR)?.throwable
    val isLoading: Boolean get() = type is ApiStateType.LOADING
    val isSuccess: Boolean get() = type is ApiStateType.SUCCESS
    val isError: Boolean get() = type is ApiStateType.ERROR
    val isIdle: Boolean get() = type is ApiStateType.IDLE

    fun execute(block: suspend () -> T) {
        type = ApiStateType.LOADING
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {
                val result = block()
                type = ApiStateType.SUCCESS(result)
            } catch (e: Exception) {
                type = ApiStateType.ERROR(e)
            }
        }
    }
}

@Composable
fun <T> rememberApiState(): ApiState<T> {
    return remember {
        ApiState()
    }
}