package com.example.findlostitemapp.ui.uploadPost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun UploadPostCard(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable () -> Unit,
    title: String = "",
    content: @Composable () -> Unit
) {
    ElevatedCard(modifier = modifier) {
        Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 32.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                HeaderIcon {
                    leadingIcon()
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, style = MaterialTheme.typography.headlineSmall)
            }
            Spacer(modifier = Modifier.size(16.dp))
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.10f))
            Spacer(modifier = Modifier.size(16.dp))
            content()
        }

    }
}

@Composable
private fun HeaderIcon(leadingIcon: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary, CircleShape),
        contentAlignment =
        Alignment
            .Center
    ) {
        leadingIcon()
    }
}