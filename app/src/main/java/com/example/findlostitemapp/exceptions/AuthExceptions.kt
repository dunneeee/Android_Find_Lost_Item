package com.example.findlostitemapp.exceptions

sealed class AuthExceptions(message: String, code: Int) : BaseException(message, code) {
    class UserNotFound(message: String = "Người dùng không tồn tại trên hệ thống") : AuthExceptions(message, 404)
    class UserAlreadyExists(message: String = "Người dùng đã tồn tại trên hệ thống") : AuthExceptions(message, 409)
    class InvalidCredentials(message: String = "Sai tên đăng nhập hoặc mật khẩu") : AuthExceptions(message, 401)
    class Unauthorized(message: String = "Không có quyền truy cập hoặc chưa xác thực người dùng") : AuthExceptions
        (message,
        403)
    class MissingFields(message: String = "Thiếu thông tin bắt buộc") : AuthExceptions(message, 400)
}