package com.example.findlostitemapp.ui.postDetail

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.findlostitemapp.LocalSnackBar
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.hooks.rememberPostByIdState
import com.example.findlostitemapp.hooks.rememberRecommendedPostState
import com.example.findlostitemapp.providers.LocalNotification
import com.example.findlostitemapp.ui.components.PostList
import com.example.findlostitemapp.ui.components.SliderShow
import com.example.findlostitemapp.ui.components.User
import com.example.findlostitemapp.utils.FakeData
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(modifier: Modifier = Modifier, postId: String) {
    var post by remember {
        mutableStateOf<Post.Instance?>(null)
    }
    var postsRecommended by remember {
        mutableStateOf<List<Post.Instance>?>(null)
    }

    val postDetailState = rememberPostByIdState()
    val postsRecommendedState = rememberRecommendedPostState()
    val snackBarState = LocalSnackBar.current

    LaunchedEffect(Unit) {
        postDetailState.execute(postId)
        postsRecommendedState.execute(null, null)
    }

    LaunchedEffect(postDetailState.state.type) {
        if (postDetailState.state.isSuccess) {
            post = postDetailState.state.data
        }

        if (postDetailState.state.isError) {
            val error = postDetailState.state.error
            snackBarState.showSnackbar(error?.message ?: "Có lỗi xảy ra", duration = SnackbarDuration.Long)
        }

        if (postsRecommendedState.state.isSuccess) {
            postsRecommended = postsRecommendedState.state.data?.filter { it.uuid != postId } ?: emptyList()
        }

        if (postsRecommendedState.state.isError) {
            val error = postsRecommendedState.state.error
            snackBarState.showSnackbar(error?.message ?: "Có lỗi xảy ra", duration = SnackbarDuration.Long)
        }
    }

    if (post != null) {
        Scaffold(modifier = modifier, topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = post!!.user.username)
            })
        }) { paddingValues ->
            PostDetailContent(
                modifier = Modifier.padding(paddingValues),
                post = post!!,
                postsRecommended = postsRecommended
            )
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun PostDetailContent(
    modifier: Modifier = Modifier, post: Post.Instance, postsRecommended: List<Post.Instance>?
    = null
) {

    LazyColumn(modifier = modifier) {

        item {
            Row {
                User(user = post.user, modifier = Modifier.padding(start = 16.dp))
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More options")
                }
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(text = post.title, style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = post.content)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Location Icon")
                    Text(
                        text = post.location,
                        style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                        modifier = Modifier
                            .padding
                                (horizontal = 8.dp),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (post.images.isNotEmpty()) {
                SliderShow(items = post.images) { post, _ ->
                    AsyncImage(
                        model = post.url, contentDescription = post.uuid, modifier = Modifier
                            .fillMaxWidth()
                            .height
                                (300.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), thickness = 1.dp, modifier = Modifier
                    .padding(16.dp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
            ) {
                Text(text = "Bài viết gợi ý", style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp))
                Spacer(modifier = Modifier.height(16.dp))
                if (postsRecommended == null) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else if (postsRecommended.isEmpty()) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Warning, contentDescription = "Warning Icon", tint = MaterialTheme.colorScheme
                                .onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Không có bài viết gợi ý", style = MaterialTheme.typography.bodyMedium)
                    }
                } else {
                    PostList(posts = postsRecommended)
                }
            }
        }
    }

}

@Composable
fun ImagePreviewDialog(
    modifier: Modifier = Modifier, image: String, onDismissRequest: () -> Unit, isOpen: Boolean =
        false
) {
    if (isOpen) {
        Dialog(onDismissRequest = onDismissRequest) {
            AsyncImage(
                model = image, contentDescription = "Image Preview", modifier = modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
    }
}