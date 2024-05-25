package com.example.findlostitemapp.ui.topicManager

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.hooks.ValidatorItem
import com.example.findlostitemapp.hooks.rememberFormState
import com.example.findlostitemapp.ui.components.CustomTextField

@Composable
fun TopicManagerDialog(
    modifier: Modifier = Modifier, open: Boolean = false,
    onDismiss: () -> Unit = {}, onConfirm: (Post.Topic) -> Unit = {},
    topic: Post.Topic? = null
) {
    val formState = rememberFormState(defaultValue = TextFieldValue(""))

    val handleOnConfirm = {
        val name = formState.getValue("name").text
        val uuid = formState.getValue("uuid", TextFieldValue("")).text
        onConfirm(Post.Topic(uuid = uuid, name = name, createdAt = "", updatedAt = ""))
        onDismiss()
        formState.clear()
    }

    LaunchedEffect(Unit) {
        formState.registerField(ValidatorItem(key = "name", validator = {
            if (it.text.isEmpty()) {
                "Tên chủ đề không được để trống"
            } else {
                null
            }
        }))
        if (topic != null) {
            formState.registerField(ValidatorItem(key = "uuid", validator = {
                if (it.text.isEmpty()) {
                    "Uuid không được để trống"
                } else {
                    null
                }
            }))
        }
    }

    if (open) {
        AlertDialog(modifier = modifier, onDismissRequest = onDismiss, confirmButton = {
            TextButton(onClick = handleOnConfirm, enabled = formState.isValid()) {
                Text(
                    text = "Xác nhận",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                )
            }
        }, title = {
            val text = if (topic == null) "Thêm chủ đề" else "Chỉnh sửa chủ đề"
            Text(text = text, style = MaterialTheme.typography.headlineSmall)
        }, text = {
            Column {
                if (topic != null) {
                    CustomTextField(
                        error = formState.getError("uuid"),
                        value = formState.getValue("uuid"), onValueChange = {
                            formState.setValue("uuid", it)
                        }, label = "Uuid", readOnly = true
                    )
                }
                CustomTextField(value = formState.getValue("name"), onValueChange = {
                    formState.setValue("name", it)
                }, error = formState.getError("name"), label = "Tên chủ đề")
            }
        })
    }
}