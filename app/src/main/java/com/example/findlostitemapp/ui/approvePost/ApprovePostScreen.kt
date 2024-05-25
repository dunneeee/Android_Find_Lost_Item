package com.example.findlostitemapp.ui.approvePost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.LocalSnackBar
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.hooks.rememberChangeStatusPostState
import com.example.findlostitemapp.hooks.rememberGetPendingPostState
import com.example.findlostitemapp.ui.MainLayout
import com.example.findlostitemapp.ui.components.DataNotFound
import kotlinx.coroutines.launch

@Composable
fun ApprovePostScreen() {
    MainLayout(topBar = {
        ApprovePostTopBar()
    }) {
        ApprovePostContent(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun ApprovePostContent(modifier: Modifier = Modifier) {
    val getPendingPostState = rememberGetPendingPostState()
    val changeStatusPostState = rememberChangeStatusPostState()
    val snackBarState = LocalSnackBar.current
    LaunchedEffect(Unit) {
        getPendingPostState.execute()
    }

    val handleRejectPost = { post: Post.Instance ->
        changeStatusPostState.rejectPost(post.uuid)
    }

    val handleAcceptPost = { post: Post.Instance ->
        changeStatusPostState.acceptPost(post.uuid)
    }

    LaunchedEffect(changeStatusPostState.state.type) {
        if (changeStatusPostState.state.isError) {
            snackBarState.showSnackbar(message = changeStatusPostState.state.error?.message ?: "Đã có lỗi xảy ra")
        }

        if (changeStatusPostState.state.isSuccess) {
            val post = changeStatusPostState.state.data!!
            launch {
                if (post.status == Post.Status.REJECTED) {
                    snackBarState.showSnackbar(message = "Đã từ chối bài đăng")
                } else {
                    snackBarState.showSnackbar(message = "Đã duyệt bài đăng")
                }
            }

            getPendingPostState.state.data = getPendingPostState.state.data?.filter { it.uuid != post.uuid }
        }
    }

    Column(modifier = modifier.padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Các bài đăng", style = MaterialTheme.typography.headlineSmall)
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        if (getPendingPostState.state.isLoading) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator()
            }
        } else if (getPendingPostState.state.isSuccess && getPendingPostState.state.data!!.isEmpty()) {
            DataNotFound(text = "Không có bài đăng nào!")
        } else {
            getPendingPostState.state.data?.forEachIndexed { _, post ->
                ApprovePostItem(
                    post = post, modifier = Modifier.padding(vertical = 8.dp), onReject =
                    handleRejectPost, onAccept = handleAcceptPost,
                    enableActions = !changeStatusPostState.state.isLoading
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApprovePostTopBar(modifier: Modifier = Modifier) {
    TopAppBar(title = {
        Text(text = "Phê duyệt bài đăng")
    }, modifier = modifier)
}