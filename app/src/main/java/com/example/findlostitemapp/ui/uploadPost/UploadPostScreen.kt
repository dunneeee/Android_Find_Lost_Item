package com.example.findlostitemapp.ui.uploadPost

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox

import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.domain.model.NotificationArgs
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.navigation.LocalNavProvider
import com.example.findlostitemapp.ui.MainLayout
import com.example.findlostitemapp.ui.auth.AuthLocalStore
import com.example.findlostitemapp.ui.auth.AuthNavigation
import com.example.findlostitemapp.ui.components.CustomTextField
import com.example.findlostitemapp.ui.components.CustomTextFieldType
import com.example.findlostitemapp.ui.components.ImageUpload
import com.example.findlostitemapp.ui.components.SelectDropDown
import com.example.findlostitemapp.ui.home.HomeNavigation
import com.example.findlostitemapp.hooks.rememberFormState
import com.example.findlostitemapp.hooks.rememberTopicSelectState
import com.example.findlostitemapp.hooks.rememberUploadPostState
import com.example.findlostitemapp.providers.LocalNotification

@Composable
fun UploadPostScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val authStore = AuthLocalStore(context)
    val navigation = LocalNavProvider.current
    if (!authStore.isLoggedIn()) {
        navigation.navigate(AuthNavigation.loginRoute.path) {
            popUpTo(HomeNavigation.route.path)
        }
        return
    }


    MainLayout(modifier = modifier, topBar = {
        UploadPostTopBar()
    }) {
        UploadPostScreenContent()
    }
}


@Composable
fun UploadPostScreenContent(modifier: Modifier = Modifier) {
    val notifyState = LocalNotification.current
    val navigation = LocalNavProvider.current

    val topicState = rememberTopicSelectState()
    val uploadState = rememberUploadPostState()
    val formState = rememberFormState(TextFieldValue(""))
    {
        it.text.isNotEmpty()
    }

    val isValidate = formState.isValid()

    val selectedImages = rememberSaveable { mutableStateOf(emptyList<Uri>()) }

    selectedImages.value.forEach {
        println(it.path)
    }

    val onImagesSelected: (List<Uri>) -> Unit = {
        selectedImages.value += it
        selectedImages.value.distinct()
    }

    val onImageClick: (Uri) -> Unit = {
        selectedImages.value = selectedImages.value.filter { image -> image != it }
    }

    val clearForm = {
        formState.clear()
        selectedImages.value = emptyList()
    }

    val handleUploadPost = {
        if (isValidate) {
            val payload = Post.Upload(
                title = formState.getValue(UploadPostValidators.Keys.TITLE).text,
                content = formState.getValue(UploadPostValidators.Keys.CONTENT).text,
                location = formState.getValue(UploadPostValidators.Keys.LOCATION).text,
                topic = formState.getValue(UploadPostValidators.Keys.TOPIC).text,
                images = selectedImages.value
            )
            uploadState.execute(payload)
        }
    }

    LaunchedEffect(Unit) {
        formState.registerField(UploadPostValidators.validator)
    }

    LaunchedEffect(uploadState.state.type) {
        if (uploadState.state.isSuccess) {
            clearForm()
            notifyState.showNotification(
                NotificationArgs(
                    title = "Đăng bài thành công",
                    content = {
                        Text(text = "Bài viết của bạn đã được đăng thành công và đang chờ phê duyệt!")
                    },
                    dismissText = "Đóng",
                    confirmText = "Về trang chủ",
                    onConfirm = {
                        navigation.navigate(HomeNavigation.route.path)
                    }
                )
            )
        }

        if (uploadState.state.isError) {
            notifyState.showNotification(
                NotificationArgs(
                    title = "Đăng bài thất bại",
                    content = {
                        Text(text = uploadState.state.error?.message ?: "Đã có lỗi xảy ra")
                    },
                    dismissText = "Đóng"
                )
            )
        }
    }

    Column(modifier = modifier) {

        UploadPostCard(
            title = "Thông tin cơ bản",
            leadingIcon = {
                Icon(Icons.Filled.AccountBox, contentDescription = "Back", tint = Color.White)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            UploadPostTextField(
                value =
                formState.getValue(UploadPostValidators.Keys.TITLE), onValueChange = {
                    formState.setValue(UploadPostValidators.Keys.TITLE, it)
                }, label = "Tiêu đề",
                error = formState.getError(UploadPostValidators.Keys.TITLE)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Chủ đề", fontWeight = FontWeight.Bold)
            SelectDropDown(
                options = topicState.options,
                selectedOption = topicState.selected,
                modifier = Modifier.fillMaxWidth(),
                onOptionSelected = {
                    topicState.handleSelect(it)
                    formState.setValue(UploadPostValidators.Keys.TOPIC, TextFieldValue(it.value))
                }, colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                type = CustomTextFieldType.Default,
                error = formState.getError(UploadPostValidators.Keys.TOPIC)
            )
            Spacer(modifier = Modifier.height(16.dp))
            UploadPostTextField(
                value =
                formState.getValue(UploadPostValidators.Keys.CONTENT), onValueChange = {
                    formState.setValue(UploadPostValidators.Keys.CONTENT, it)
                }, label = "Nội dung",
                error = formState.getError(UploadPostValidators.Keys.CONTENT)
            )
        }

        UploadPostCard(leadingIcon = {
            Icon(Icons.Default.LocationOn, contentDescription = "Location Icon")
        }, title = "Địa điểm", modifier = Modifier.padding(16.dp)) {
            UploadPostTextField(
                value =
                formState.getValue(UploadPostValidators.Keys.LOCATION), onValueChange = {
                    formState.setValue(UploadPostValidators.Keys.LOCATION, it)
                }, label = "Địa chỉ",
                error = formState.getError(UploadPostValidators.Keys.LOCATION),
                isLasted = true
            )
        }
    }


    UploadPostCard(leadingIcon = {
        Icon(Icons.Default.MailOutline, contentDescription = "Mail Icon")
    }, title = "Thêm hình ảnh", modifier = Modifier.padding(16.dp)) {
        ImageUpload(
            selectedImages = selectedImages.value, onImagesSelected = onImagesSelected, onImageClick =
            onImageClick
        )
    }



    Button(
        onClick = handleUploadPost, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), shape = MaterialTheme
            .shapes
            .medium,
        enabled = isValidate && uploadState.state.isLoading.not()
    ) {
        Text(text = "Đăng bài")
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPostTopBar(modifier: Modifier = Modifier) {
    TopAppBar(modifier = modifier, title = {
        Text(text = "Tải bài viết lên")
    })
}


@Composable
private fun UploadPostTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    error: String? = null,
    isLasted: Boolean = false
) {
    Column {
        Text(text = label, fontWeight = FontWeight.Bold)
        CustomTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            type = CustomTextFieldType.Default,
            error = error,
            singleLine = false,
            isLasted = isLasted
        )
    }
}