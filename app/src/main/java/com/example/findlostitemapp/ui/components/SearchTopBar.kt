package com.example.findlostitemapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.domain.model.SearchData
import com.example.findlostitemapp.hooks.rememberSearchPostState
import com.example.findlostitemapp.navigation.LocalNavProvider
import com.example.findlostitemapp.ui.posts.PostsNavigation
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier, title: @Composable () -> Unit = {}, actions: @Composable () -> Unit =
        {}
) {
    val coroutineScope = rememberCoroutineScope()
    val searchState = rememberSearchPostState()

    var isSearching by remember { mutableStateOf(false) }
    var job by remember {
        mutableStateOf<Job?>(null)
    }
    var currentQuery by remember {
        mutableStateOf("")
    }

    val navigation = LocalNavProvider.current
    val openSearching = {
        isSearching = true
    }

    val closeSearching = {
        isSearching = false
    }

    val handleQueryChange = { query: String ->
        currentQuery = query
    }

    val handleSearch = { query: String ->
        navigation.navigate(PostsNavigation.route.withQueryParams(item = query))
    }

    LaunchedEffect(currentQuery) {
        job?.cancel()
        job = coroutineScope.launch {
            searchState.execute(SearchData(item = currentQuery, topic = "", location = ""))
        }
    }

    if (isSearching) {
        SearchBar(
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search Icon")
            },
            trailingIcon = {
                IconButton(onClick = { closeSearching() }) {
                    Icon(Icons.Outlined.Close, contentDescription = "Close Search Icon")
                }
            },
            query = currentQuery,
            onQueryChange = handleQueryChange,
            onSearch = handleSearch,
            active = true,
            onActiveChange = { isSearching = it }, modifier = modifier,
            placeholder = { Text("Nhập tên đồ vật cần tìm") }
        ) {

        }
        return
    }

    TopAppBar(
        title = title,
        actions = {
            IconButton(onClick = { openSearching() }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
            actions()
        }, modifier = modifier
    )
}

@Composable
private fun SearchTopBarItem(modifier: Modifier = Modifier, post: Post.Instance) {
    Column(modifier = modifier) {

    }
}