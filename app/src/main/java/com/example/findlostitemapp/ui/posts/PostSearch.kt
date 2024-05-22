package com.example.findlostitemapp.ui.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.domain.model.SearchData
import com.example.findlostitemapp.hooks.rememberTopicSelectState
import com.example.findlostitemapp.ui.components.OutlineSelectDropDown
import com.example.findlostitemapp.ui.components.SelectOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostSearch(
    modifier: Modifier = Modifier, onValueChange: (SearchData) -> Unit = {}, searchData: SearchData
) {
    val topicState = rememberTopicSelectState()

    var searchQuery by remember {
        mutableStateOf(searchData.item)
    }

    var currentLocation by remember {
        mutableStateOf(searchData.location)
    }


    var openFilterMenu by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(searchData, topicState.options) {
        val optionSelected = topicState.options.find { it.value == searchData.topic }
        println(optionSelected)
        if (optionSelected != null) {
            topicState.selected = optionSelected
        }
    }


    val handleSearchQueryChange = { value: String ->
        searchQuery = value
        onValueChange(SearchData(value, currentLocation, topicState.selected.value))
    }

    val handleLocationChange = { value: String ->
        currentLocation = value
        onValueChange(SearchData(searchQuery, value, topicState.selected.value))
    }

    val handleTopicChange = { value: SelectOption<String> ->
        onValueChange(SearchData(searchQuery, currentLocation, value.value))
    }

    Column(modifier = modifier) {
        TextField(value = searchQuery, onValueChange = handleSearchQueryChange, label = {
            Text(text = "Tìm kiếm....")
        }, leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon")
        },
            trailingIcon = {
                IconButton(onClick = {
                    openFilterMenu = true
                }) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = "Filter Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            singleLine = true, modifier = Modifier.fillMaxWidth()
        )
    }

    if (openFilterMenu) {
        ModalBottomSheet(onDismissRequest = {
            openFilterMenu = false
        }, dragHandle = {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                BottomSheetDefaults.DragHandle()
                Text(text = "Filter")
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
            }
        }, modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                OutlinedTextField(
                    value = currentLocation,
                    onValueChange = handleLocationChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Tìm ở")
                    },
                    leadingIcon = {
                        Icon(Icons.Default.LocationOn, contentDescription = "Location Icon")
                    }, singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlineSelectDropDown(
                    options = topicState.options,
                    selectedOption = topicState.selected,
                    leadingIcon = {
                        Icon(Icons.Default.Create, contentDescription = "List Icon")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    onOptionSelected = {
                        topicState.handleSelect(it)
                        handleTopicChange(it)
                    },
                    label = {
                        Text(text = "Chủ đề")
                    })

            }
        }
    }
}