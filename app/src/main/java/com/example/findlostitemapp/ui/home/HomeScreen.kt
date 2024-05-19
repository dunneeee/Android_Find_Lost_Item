package com.example.findlostitemapp.ui.home

import androidx.compose.foundation.Image

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.findlostitemapp.LocalSnackBar

import com.example.findlostitemapp.R
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.domain.model.SearchData
import com.example.findlostitemapp.hooks.rememberPostCountState
import com.example.findlostitemapp.hooks.rememberRecommendedPostState
import com.example.findlostitemapp.hooks.rememberUserCountState
import com.example.findlostitemapp.navigation.LocalNavProvider
import com.example.findlostitemapp.ui.MainLayout
import com.example.findlostitemapp.ui.components.PostList
import com.example.findlostitemapp.ui.components.SearchTopBar
import com.example.findlostitemapp.ui.posts.PostsNavigation

import com.example.findlostitemapp.ui.theme.FindLostItemAppTheme


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    MainLayout(topBar = {
        HomeTopBar()
    }, modifier = modifier) {
        HomeContent(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun HomeContent(modifier: Modifier = Modifier) {

    var posts by remember {
        mutableStateOf<List<Post.Instance>>(emptyList())
    }
    var postCount by remember {
        mutableIntStateOf(0)
    }
    var userCount by remember {
        mutableIntStateOf(0)
    }

    val navigation = LocalNavProvider.current
    val snackBarState = LocalSnackBar.current

    val postsState = rememberRecommendedPostState()
    val postCountState = rememberPostCountState()
    val userCountState = rememberUserCountState()

    val handleSearchClick: (SearchData) -> Unit = {
        navigation.navigate(PostsNavigation.route.withQueryParams(it.item, it.location, it.topic))
    }

    val handleViewAllClick: () -> Unit = {
        navigation.navigate(PostsNavigation.route.withQueryParams())
    }

    LaunchedEffect(Unit) {
        postsState.execute(null, null)
        postCountState.execute()
        userCountState.execute()
    }

    LaunchedEffect(postsState.state.type) {
        if (postsState.state.isSuccess && postsState.state.data != null) {
            posts = postsState.state.data!!
        }

        if (postsState.state.isError) {
            val error = postsState.state.error
            snackBarState.showSnackbar(error?.message ?: "Có lỗi xảy ra", duration = SnackbarDuration.Long)
            posts = emptyList()
        }
    }

    LaunchedEffect(postCountState.state.type) {
        if (postCountState.state.isSuccess) {
            postCount = postCountState.state.data!!
        }
    }

    LaunchedEffect(userCountState.state.type) {
        if(userCountState.state.isSuccess) {
            userCount = userCountState.state.data!!
        }
    }

    Column(modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.home_background),
                contentDescription = "Home Background",
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .padding(top = 80.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tìm đồ thất lạc trực tuyến",
                    style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tìm đồ thất lạc ở muôn nơi một cách dễ dàng",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontSize = 18.sp
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                HomeSearch(onSearchClick = handleSearchClick)
            }
        }



        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            HomeAboutUs(modifier = Modifier.padding(16.dp))
        }


        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Bài đăng gần đây", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = handleViewAllClick) {
                        Text(
                            text = "Xem tất cả", style = MaterialTheme.typography.bodyLarge.copy(
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (postsState.state.isLoading) {
                    CircularProgressIndicator()
                } else if (posts.isEmpty()) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "No data found",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Không có bài đăng nào")
                } else {
                    PostList(posts = posts)
                }
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Column {
                Text(
                    text = "Thống kê",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(modifier = Modifier.height(8.dp))
                CountUser(count = postCount, title = "Số bài đăng")
                Spacer(modifier = Modifier.height(16.dp))
                CountUser(count = userCount, title = "Số người dùng")
            }
        }
    }
}


@Composable
fun HomeTopBar(modifier: Modifier = Modifier) {
    SearchTopBar(modifier = modifier, title = {
        Text(text = "Trang chủ")
    })

}


@Preview
@Composable
private fun HomeScreenPreview() {
    FindLostItemAppTheme {
        HomeScreen()
    }
}