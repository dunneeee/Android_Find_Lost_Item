package com.example.findlostitemapp.exceptions

sealed class TopicExceptions(message: String, code: Int) : BaseException(message, code) {
    class MissingFieldException(message: String = "Thiếu dữ liệu!") : TopicExceptions(message, 400)
    class TopicNotFoundException(message: String = "Không tìm thấy topic này!") : TopicExceptions(message, 404)
}