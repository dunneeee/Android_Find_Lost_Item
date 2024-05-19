package com.example.findlostitemapp.domain.model

object Http {
    data class Payload<T>(
        val code: Int,
        val message: String?,
        val data: T?,
        val error: String? = null
    )

    data class Error<T>(val code: Int, val message: String?, val data: Any? = null, val error: T)
}