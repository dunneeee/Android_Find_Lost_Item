package com.example.findlostitemapp.exceptions

sealed class ApiExceptions(message: String, code: Int) : BaseException(message, code) {
    class InternalServerError : ApiExceptions("Lỗi hệ thống", 500)
}