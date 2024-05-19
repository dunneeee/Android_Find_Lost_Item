package com.example.findlostitemapp.ui.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.domain.model.SearchData
import com.example.findlostitemapp.ui.MainLayout
import com.example.findlostitemapp.ui.components.PostList
import com.example.findlostitemapp.ui.components.PostListDirection
import com.example.findlostitemapp.utils.FakeData

@Composable
fun PostsScreen(modifier: Modifier = Modifier, searchQuery: SearchData) {
    MainLayout(modifier = modifier, topBar = {
        PostsTopBar()
    }) {
        PostsContent(
            modifier = Modifier
                .fillMaxSize(),
            searchQuery = searchQuery
        )
    }
}

@Composable
fun PostsContent(modifier: Modifier = Modifier, searchQuery: SearchData) {
    val posts = remember {
        FakeData.getPosts()
    }
    Column(modifier = modifier.padding(16.dp)) {

        PostSearch(modifier = Modifier.padding(bottom = 16.dp, start = 8.dp, end = 8.dp), searchData = searchQuery)

        PostList(posts = posts, direction = PostListDirection.VERTICAL)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsTopBar(modifier: Modifier = Modifier) {
    TopAppBar(title = {
        Text(text = "Posts")
    }, modifier = modifier)
}