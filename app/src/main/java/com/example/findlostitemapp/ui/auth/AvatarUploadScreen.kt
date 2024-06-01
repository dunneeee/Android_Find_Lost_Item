package com.example.findlostitemapp.ui.auth

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.findlostitemapp.LocalSnackBar
import com.example.findlostitemapp.domain.model.NotificationArgs
import com.example.findlostitemapp.hooks.rememberChangeUserAvatarState
import com.example.findlostitemapp.hooks.rememberImageResultLauncher
import com.example.findlostitemapp.navigation.LocalNavProvider
import com.example.findlostitemapp.providers.LocalNotification
import com.example.findlostitemapp.ui.home.HomeNavigation
import com.example.findlostitemapp.ui.theme.FindLostItemAppTheme

@Composable
fun AvatarUploadScreen() {
    val uploadState = rememberChangeUserAvatarState()
    val navigation = LocalNavProvider.current
    val notifyState = LocalNotification.current
    val snackBarState = LocalSnackBar.current
    val handleUploadAvatar = { uri: Uri? ->
        if (uri != null) {
            uploadState.execute(uri)
        } else {
            navigation.navigate(HomeNavigation.route.path)
        }

    }

    LaunchedEffect(key1 = uploadState.state.type) {
        if (uploadState.state.isError) {
            notifyState.showNotification(NotificationArgs(
                title = "Có lỗi xảy ra khi tải ảnh lên",
                content = {
                    Text(text = uploadState.state.error?.message ?: "Có lỗi xảy ra khi tải ảnh lên")
                }
            ))
            navigation.navigate(HomeNavigation.route.path)
        }

        if (uploadState.state.isSuccess) {
            snackBarState.showSnackbar("Tải ảnh lên thành công", duration = SnackbarDuration.valueOf("2000"))
            navigation.navigate(HomeNavigation.route.path)
        }

    }

    Scaffold(topBar = {
        AvatarUploadTopBar()
    }) { paddingValues ->
        AvatarUploadContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            onUploadClick = handleUploadAvatar,
            isUploading = uploadState.state.isLoading
        )
    }
}


@Composable
fun AvatarUploadContent(
    modifier: Modifier = Modifier, onImageSelected: (Uri?) -> Unit = {}, onUploadClick: (Uri?) ->
    Unit = {},
    isUploading: Boolean = false
) {
    var imageSelected by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberImageResultLauncher() { uri ->
        imageSelected = uri
        onImageSelected(uri)
    }

    Surface(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .border(BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface), CircleShape)
                    .clip(CircleShape)
                    .clickable {
                        launcher.launch("image/*")
                    }
            ) {
                AsyncImage(
                    model = imageSelected,
                    contentDescription = "Upload avatar",
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onUploadClick(imageSelected) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isUploading
            ) {
                Text(text = "Tải ảnh lên")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvatarUploadTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(title = {
        Text(text = "Tải ảnh đại diện")
    }, navigationIcon = {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
    }, modifier = modifier)
}

@Preview
@Composable
private fun AvatarUploadPreview() {
    FindLostItemAppTheme {
        AvatarUploadScreen()
    }
}