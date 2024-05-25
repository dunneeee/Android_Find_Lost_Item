package com.example.findlostitemapp.exceptions

sealed class ApiExceptions(message: String, code: Int) : BaseException(message, code) {
    class InternalServerError : ApiExceptions("Lỗi hệ thống", 500)
    class NotFound : ApiExceptions("Không tìm thấy dữ liệu", 404)
}