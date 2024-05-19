package com.example.findlostitemapp.ui.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.findlostitemapp.hooks.rememberImageResultLauncher
import com.example.findlostitemapp.hooks.rememberImagesResultLauncher
import com.example.findlostitemapp.ui.theme.FindLostItemAppTheme

@Composable
fun ImageUpload(
    modifier: Modifier = Modifier, selectedImages: List<Uri> = emptyList(), onImagesSelected: (
        List<Uri>
    )
    -> Unit = {},
    onImageClick: (Uri) -> Unit = {},
    singleImage: Boolean = false
) {
    val strokeColor = MaterialTheme.colorScheme.onPrimary
    val stroke = Stroke(width = 4.dp.value, pathEffect = getLinePathEffect())

    val rows = remember(selectedImages) {
        derivedStateOf {
            selectedImages.chunked(3)
        }
    }

    val launcher = if (singleImage) rememberImageResultLauncher() {
        if (it != null) onImagesSelected(listOf(it))
        else onImagesSelected(emptyList())
    } else rememberImagesResultLauncher() {
        onImagesSelected(it)
    }

    Box(modifier = modifier
        .drawBehind {
            drawRect(color = strokeColor, style = stroke)
        }
        .clickable {
            launcher.launch("image/*")
        }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (selectedImages.isNotEmpty()) {
                    Text(
                        text = "Nhấn vào hình ảnh để xóa", modifier = Modifier.padding(8.dp), style = MaterialTheme
                            .typography.bodyMedium.copy(fontStyle = FontStyle.Italic)
                    )
                }
                rows.value.forEach { row ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        row.forEach { uri ->
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(100.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .clickable {
                                        onImageClick(uri)
                                    }
                            ) {
                                AsyncImage(
                                    model = uri,
                                    contentDescription = "Image $uri",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

            }
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Up Arrow")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Chọn hình ảnh từ thư viện")
        }
    }
}


private fun getLinePathEffect(): PathEffect {
    return PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
}


@Preview
@Composable
private fun ImageUploadPreview() {
    FindLostItemAppTheme {
        ImageUpload()
    }
}