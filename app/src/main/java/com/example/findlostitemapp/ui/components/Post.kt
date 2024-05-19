package com.example.findlostitemapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.ui.theme.FindLostItemAppTheme
import com.example.findlostitemapp.utils.FakeData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Post(
    modifier: Modifier = Modifier, post: Post.Instance, onEditClick: () -> Unit = {}, onDeleteClick: () -> Unit = {},
    onClick: ()
    ->
    Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    val toggleExpanded = { expanded = !expanded }

    ElevatedCard(modifier = modifier, onClick = onClick) {
        Column {
            Row(modifier = Modifier.padding(top = 8.dp, start = 8.dp)) {
                User(user = post.user)
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    IconButton(onClick = toggleExpanded) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                    }

                    DropdownMenu(expanded = expanded, onDismissRequest = toggleExpanded) {
                        DropdownMenuItem(text = {
                            Text(text = "Edit")
                        }, onClick = onEditClick, leadingIcon = {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Icon")
                        })
                        DropdownMenuItem(text = {
                            Text(text = "Delete")
                        }, onClick = onDeleteClick, leadingIcon = {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Icon")

                        })
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.title, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding
                    (horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.content, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding
                    (horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                Icon(Icons.Default.LocationOn, contentDescription = "Location Icon")
                Text(
                    text = post.location,
                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                    modifier = Modifier
                        .padding
                            (horizontal = 8.dp),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.height(300.dp), contentAlignment = Alignment.Center) {
                if (post.images.isNotEmpty()) {
                    AsyncImage(
                        model = post.images[0].url,
                        contentDescription = post.title,
                        modifier = Modifier
                            .fillMaxSize()
                            .height(300.dp),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "No image found",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Không có hình ảnh")

                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PostPreview() {
    FindLostItemAppTheme {
        Post(post = FakeData.getRandPost())
    }
}

@Preview
@Composable
private fun RowPostPreview() {

}