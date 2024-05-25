package com.example.findlostitemapp.ui.approvePost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.domain.model.NotificationArgs
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.navigation.LocalNavProvider
import com.example.findlostitemapp.providers.LocalNotification
import com.example.findlostitemapp.ui.components.ExpandableText
import com.example.findlostitemapp.ui.components.User
import com.example.findlostitemapp.ui.postDetail.PostDetailNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApprovePostItem(
    modifier: Modifier = Modifier, post: Post.Instance, onReject: (post: Post.Instance) -> Unit = {},
    onAccept: (post: Post.Instance) -> Unit = {},
    enableActions: Boolean = true
) {
    val navigation = LocalNavProvider.current
    val notifyState = LocalNotification.current

    val handleReject = {
        notifyState.showNotification(NotificationArgs(
            title = "Từ chối bài đăng",
            content = {
                Text(text = "Bạn có chắc chắn muốn từ chối bài đăng này không?")
            },
            onConfirm = {
                onReject(post)
            }
        ))
    }

    val handleAccept = {
        notifyState.showNotification(NotificationArgs(
            title = "Duyệt bài đăng",
            content = {
                Text(text = "Bạn có chắc chắn muốn duyệt bài đăng này không?")
            },
            onConfirm = {
                onAccept(post)
            }
        ))
    }

    ElevatedCard(modifier = modifier.fillMaxWidth(), onClick = {
        navigation.navigate(PostDetailNavigation.route.withArgs(post.uuid))
    }) {
        Column(modifier = Modifier.padding(8.dp)) {
            User(user = post.user)
            Text(text = post.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            ExpandableText(text = post.content)
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = handleReject, enabled = enableActions) {
                    Text(text = "Từ chối", color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Button(onClick = handleAccept, enabled = enableActions) {
                    Text(text = "Duyệt")
                }
            }
        }
    }
}