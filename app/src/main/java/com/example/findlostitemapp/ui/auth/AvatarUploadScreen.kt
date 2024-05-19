package com.example.findlostitemapp.ui.auth

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.findlostitemapp.R
import com.example.findlostitemapp.hooks.rememberImageResultLauncher
import com.example.findlostitemapp.ui.theme.FindLostItemAppTheme

@Composable
fun AvatarUploadScreen() {
    Scaffold(topBar = {
        AvatarUploadTopBar()
    }) { paddingValues ->
        AvatarUploadContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}


@Composable
fun AvatarUploadContent(
    modifier: Modifier = Modifier, onImageSelected: (Uri?) -> Unit = {}, onUploadClick: (Uri?) ->
    Unit = {}
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
            Button(onClick = { onUploadClick(imageSelected) }, modifier = Modifier.fillMaxWidth()) {
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