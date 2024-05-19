package com.example.findlostitemapp.ui.uploadPost

import androidx.compose.ui.text.input.TextFieldValue
import com.example.findlostitemapp.hooks.ValidatorItem

object UploadPostValidators {
    object Keys {
        const val TITLE = "title"
        const val CONTENT = "content"
        const val TOPIC = "topic"
        const val LOCATION = "location"
        const val IMAGES = "images"
    }

    val validator = listOf<ValidatorItem<TextFieldValue>>(
        ValidatorItem(key = Keys.TITLE, validator = {
            val text = it.text.trim()

            if (text.isEmpty()) {
                "Vui lòng nhập tiêu đề"
            } else if (text.length < 6) {
                "Tiêu đề phải có ít nhất 6 ký tự"
            } else
                null
        }),
        ValidatorItem(Keys.CONTENT, validator = {
            val text = it.text.trim()

            if (text.isEmpty()) {
                "Vui lòng nhập mô tả"
            } else if (text.length < 6) {
                "Mô tả phải có ít nhất 6 ký tự"
            } else
                null
        }),
        ValidatorItem(key = Keys.TOPIC, validator = {
            val text = it.text.trim()

            if (text.isEmpty()) {
                "Vui lòng chọn chủ đề"
            } else
                null
        }),
        ValidatorItem(key = Keys.LOCATION, validator = {
            val text = it.text.trim()

            if (text.isEmpty()) {
                "Vui lòng nhập địa chỉ"
            } else
                null
        }),
    )
}