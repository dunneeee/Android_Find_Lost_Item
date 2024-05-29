package com.example.findlostitemapp.exceptions

sealed class PostExceptions(message: String, code: Int) : BaseException(message, code) {
    class PostNotFoundException(message: String = "Không tìm thấy bài viết!") : PostExceptions(message, 404)
    class CantAccessPostException(message: String = "Không thể truy cập bài viết!") : PostExceptions(message, 403)

    class MissingFieldException(message: String = "Thiếu trường bắt buộc!") : PostExceptions(message, 400)
}