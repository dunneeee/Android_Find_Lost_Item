
package com.example.findlostitemapp.ui.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.findlostitemapp.navigation.LocalNavProvider
import com.example.findlostitemapp.ui.posts.PostsNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier, title: @Composable () -> Unit = {}, actions: @Composable () -> Unit =
        {}
) {
    var isSearching by remember { mutableStateOf(false) }
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

    val handleQueryChange =  {
        query: String ->
        println(query)
        currentQuery = query
    }

    val handleSearch = {
        query : String ->
        navigation.navigate(PostsNavigation.route.withQueryParams(item = query))
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