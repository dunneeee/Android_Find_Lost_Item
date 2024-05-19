package com.example.findlostitemapp.ui.auth

import android.util.Patterns
import androidx.compose.ui.text.input.TextFieldValue
import com.example.findlostitemapp.hooks.FormState
import com.example.findlostitemapp.hooks.ValidatorItem

object AuthValidators {

    val extendRules: FormState<TextFieldValue>.(TextFieldValue) -> Boolean = {
        it.text.isNotEmpty()
    }

    object Keys {
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val EMAIL = "email"
        const val CONFIRM_PASSWORD = "confirmPassword"

    }

    val loginValidator = listOf<ValidatorItem<TextFieldValue>>(
        ValidatorItem(key = Keys.USERNAME, validator = {
            val text = it.text.trim()

            if (text.isEmpty()) {
                "Vui lòng nhập tên đăng nhập"
            } else if (text.length < 4) {
                "Tên đăng nhập phải có ít nhất 6 ký tự"
            } else
                null
        }),
        ValidatorItem(key = Keys.PASSWORD, validator = {
            val text = it.text.trim()

            if (text.isEmpty()) {
                "Vui lòng nhập mật khẩu"
            } else if (text.length < 6) {
                "Mật khẩu phải có ít nhất 6 ký tự"
            } else
                null
        })
    )

    val registerValidator = listOf<ValidatorItem<TextFieldValue>>(
        ValidatorItem(key = Keys.USERNAME, validator = {
            if (it.text.length < 4) "Tên đăng nhập phải lớn hơn 6 ký tự"
            else null
        }),
        ValidatorItem(Keys.EMAIL, validator = {
            if (Patterns.EMAIL_ADDRESS.matcher(it.text).matches()) null
            else "Email không đúng định dạng!"
        }),
        ValidatorItem(key = Keys.PASSWORD, validator = {
            if (it.text.length < 6) "Mật khẩu phải lớn hơn 6 ký tự"
            else null
        }),
        ValidatorItem(key = Keys.CONFIRM_PASSWORD, validator = {
            if (it.text != getValue("password", TextFieldValue("")).text) "Mật khẩu không khớp"
            else null
        })

    )
}