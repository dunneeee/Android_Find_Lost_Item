package com.example.findlostitemapp.ui.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.LocalSnackBar
import com.example.findlostitemapp.domain.model.SearchData
import com.example.findlostitemapp.hooks.rememberSearchPostState
import com.example.findlostitemapp.ui.MainLayout
import com.example.findlostitemapp.ui.components.DataNotFound
import com.example.findlostitemapp.ui.components.PostList
import com.example.findlostitemapp.ui.components.PostListDirection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val searchPostState = rememberSearchPostState()
    val snackBarState = LocalSnackBar.current
    var job by remember {
        mutableStateOf<Job?>(null)
    }

    val handleSearchValueChange = { value: SearchData ->
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            delay(500)
            searchPostState.execute(value)
        }
    }

    LaunchedEffect(searchPostState.state.type) {
        if (searchPostState.state.isError) {
            snackBarState.showSnackbar("Error: ${searchPostState.state.error?.message}")
        }
    }

    LaunchedEffect(Unit) {
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            searchPostState.execute(searchQuery)
        }
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        PostSearch(
            modifier = Modifier.padding(bottom = 16.dp, start = 8.dp, end = 8.dp),
            searchData = searchQuery,
            onValueChange = handleSearchValueChange
        )

        if (searchPostState.state.isLoading) {
            CircularProgressIndicator()
        } else {
            if (searchPostState.state.data == null || searchPostState.state.data!!.isEmpty()) {
                DataNotFound()
            } else {
                PostList(posts = searchPostState.state.data!!, direction = PostListDirection.VERTICAL)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsTopBar(modifier: Modifier = Modifier) {
    TopAppBar(title = {
        Text(text = "Bài đăng")
    }, modifier = modifier)
}