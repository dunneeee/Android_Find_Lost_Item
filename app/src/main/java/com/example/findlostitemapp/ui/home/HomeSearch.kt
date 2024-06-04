package com.example.findlostitemapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.domain.model.SearchData
import com.example.findlostitemapp.hooks.rememberTopicSelectState
import com.example.findlostitemapp.ui.components.OutlineSelectDropDown

import com.example.findlostitemapp.ui.theme.FindLostItemAppTheme

@Composable
fun HomeSearch(modifier: Modifier = Modifier, onSearchClick: (SearchData) -> Unit = {}) {
    var itemName by remember {
        mutableStateOf("")
    }

    var location by remember {
        mutableStateOf("")
    }

    val topicState = rememberTopicSelectState()

    val handleItemNameChange = { value: String ->
        itemName = value
    }

    val handleLocationChange = { value: String ->
        location = value
    }

    val textFieldStyles = TextStyle(color = Color.Black)

    Surface(modifier, shape = RoundedCornerShape(16.dp), color = Color.White) {
        Column(modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)) {
            OutlinedTextField(value = itemName, onValueChange = handleItemNameChange, label = {
                Text(text = "Đồ vật cần tìm")
            }, leadingIcon = {
                Icon(Icons.Default.List, contentDescription = "List Icon")
            }, modifier = Modifier.fillMaxWidth(), textStyle = textFieldStyles)

            OutlineSelectDropDown(options = topicState.options,
                selectedOption = topicState.selected,
                leadingIcon = {
                    Icon(Icons.Default.Create, contentDescription = "List Icon")
                },
                modifier = Modifier.fillMaxWidth(),
                onOptionSelected = topicState.handleSelect,
                textStyle = textFieldStyles,
                label = {
                    Text(text = "Chủ đề")
                })
            OutlinedTextField(value = location, onValueChange = handleLocationChange, leadingIcon = {
                Icon(Icons.Default.LocationOn, contentDescription = "Search Icon")
            }, label = {
                Text(text = "Địa điểm")
            }, textStyle = textFieldStyles, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onSearchClick(SearchData(itemName.trim(), location.trim(), topicState.selected.value))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(4.dp),
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Tìm kiếm", color = Color.White)
            }
        }
    }
}

@Preview
@Composable
private fun HomeSearchPreview() {
    FindLostItemAppTheme {
        HomeSearch()
    }
}