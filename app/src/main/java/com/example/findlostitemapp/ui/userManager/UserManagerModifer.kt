package com.example.findlostitemapp.ui.userManager

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.findlostitemapp.domain.model.User
import com.example.findlostitemapp.hooks.rememberFormState
import com.example.findlostitemapp.ui.auth.AuthValidators
import com.example.findlostitemapp.ui.components.CustomTextField
import com.example.findlostitemapp.ui.components.ImageUpload
import com.example.findlostitemapp.ui.components.SelectDropDownMenu
import com.example.findlostitemapp.ui.components.SelectOption
import com.example.findlostitemapp.utils.FileUtils
import java.io.File

@Composable
fun UserManagerModifier(
    modifier: Modifier = Modifier,
    open: Boolean = false,
    user: User.Instance? = null,
    onDismiss: () -> Unit = {},
    onConfirm: (User.Register) -> Unit = {}
) {
    val context = LocalContext.current
    val formState = rememberFormState(TextFieldValue("")) {
        it.text.isNotEmpty()
    }
    val options = remember {
        listOf(SelectOption("1", "Admin"), SelectOption("0", "User"))
    }

    var selectedOption by remember {
        mutableStateOf(options[1])
    }

    var selectedImages by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    var imagePreview by remember {
        mutableStateOf<String?>(null)
    }

    println("UserManagerModifier: Re-Render")

    val handleConfirm = {
        if (formState.isValid()) {
            val username = formState.getValue(AuthValidators.Keys.USERNAME).text
            val password = formState.getValue(AuthValidators.Keys.PASSWORD).text
            val isAdmin = formState.getValue("isAdmin").text.toInt() == 1
            val email = formState.getValue(AuthValidators.Keys.EMAIL).text
            val avatar = if (selectedImages.isNotEmpty()) {
                FileUtils.uriToFile(context, selectedImages[0], selectedImages[0].lastPathSegment)
            } else {
                null
            }
            onConfirm(
                User.Register(
                    username = username,
                    password = password,
                    isAdmin = isAdmin,
                    email = email,
                    avatar = avatar
                )
            )
            onDismiss()
        }
    }

    val handleSelectImage = { images: List<Uri> ->
        selectedImages = images
        imagePreview = null
    }

    val handleImageClick = { uri: Uri ->
        selectedImages = selectedImages.filter { it != uri }
    }

    val handleOptionSelect = { option: SelectOption<String> ->
        selectedOption = option
        formState.setValue("isAdmin", TextFieldValue(option.value))

    }

    LaunchedEffect(Unit) {
        formState.registerField(AuthValidators.registerValidator)
        formState.unregisterField(AuthValidators.Keys.CONFIRM_PASSWORD)
    }

    LaunchedEffect(user) {
        if (user != null) {
            formState.setValue(AuthValidators.Keys.USERNAME, TextFieldValue(user.username))
            formState.setValue(AuthValidators.Keys.EMAIL, TextFieldValue(user.email))
            formState.setValue(AuthValidators.Keys.PASSWORD, TextFieldValue(""))
            formState.setValue("isAdmin", TextFieldValue(if (user.isAdmin) "1" else "0"))
            selectedOption = options.find { it.value == if (user.isAdmin) "1" else "0" }!!
            formState.unregisterField(AuthValidators.Keys.PASSWORD)
            imagePreview = user.avatar
        } else {
            formState.registerField(AuthValidators.loginValidator[1])
            formState.clear()
            formState.setValue("isAdmin", TextFieldValue("0"))
            selectedOption = options[1]
            imagePreview = null
        }

        selectedImages = emptyList()
    }

    if (open) {
        AlertDialog(onDismissRequest = onDismiss, confirmButton = {
            TextButton(onClick = handleConfirm, enabled = formState.isValid()) {
                Text(text = "Xác nhận")
            }
        }, title = {
            Text(text = if (user == null) "Thêm người dùng" else "Chỉnh sửa người dùng")
        }, text = {
            Column {
                CustomTextField(value = formState.getValue(AuthValidators.Keys.USERNAME), onValueChange = {
                    formState.setValue(AuthValidators.Keys.USERNAME, it)
                }, label = "Tên đăng nhập", error = formState.getError(AuthValidators.Keys.USERNAME), leadingIcon = {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Account Icon")
                })

                CustomTextField(
                    value = formState.getValue(AuthValidators.Keys.EMAIL), onValueChange = {
                        formState.setValue(AuthValidators.Keys.EMAIL, it)
                    },
                    label = "Email", error = formState.getError(AuthValidators.Keys.EMAIL),
                    leadingIcon = {
                        Icon(Icons.Default.MailOutline, contentDescription = "Account Icon")
                    }
                )

                if (user == null) {
                    CustomTextField(
                        value = formState.getValue(AuthValidators.Keys.PASSWORD),
                        onValueChange = {
                            formState.setValue(AuthValidators.Keys.PASSWORD, it)
                        },
                        label = "Mật khẩu",
                        isPassword = true,
                        error = formState.getError(AuthValidators.Keys.PASSWORD),
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = "Account Icon")
                        },
                        isLasted = true
                    )
                }
                SelectDropDownMenu(
                    leadingIcon = {
                        Icon(Icons.Default.Info, contentDescription = "Info Icon")
                    },
                    title = "Quyền hạn",
                    options = options, selectedOption = selectedOption, error = formState.getError
                        ("isAdmin"), onOptionSelected = handleOptionSelect
                )

                ImageUpload(
                    selectedImages = selectedImages, onImagesSelected = handleSelectImage, singleImage =
                    true, onImageClick = handleImageClick,
                    previewImages = if (imagePreview != null) listOf(imagePreview!!.toUri()) else emptyList()
                )
            }
        }, modifier = modifier.fillMaxWidth())
    }
}
