package com.example.findlostitemapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.navigation.LocalNavProvider
import com.example.findlostitemapp.ui.postDetail.PostDetailNavigation

@Composable
fun PostList(
    modifier: Modifier = Modifier,
    direction: PostListDirection = PostListDirection.HORIZONTAL,
    posts: List<Post.Instance>
) {
    val navigation = LocalNavProvider.current
    val handlePostClicked = {
        post: Post.Instance ->
        navigation.navigate(PostDetailNavigation.route.withArgs(post.uuid))
    }

    println("PostList: $posts")

    when (direction) {
        PostListDirection.HORIZONTAL -> {
            SliderShow(items = posts, modifier = modifier) { post, _ ->
                Post(post = post, onClick = { handlePostClicked(post) })
            }
        }

        PostListDirection.VERTICAL -> {
            Column {
                posts.forEach {
                    Post(post = it, onClick = { handlePostClicked(it) })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

enum class PostListDirection {
    VERTICAL,
    HORIZONTAL
}