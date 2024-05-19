package com.example.findlostitemapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.ui.components.Footer

@Composable
fun MainLayout(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(topBar = topBar, modifier = modifier) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item {
                content()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Footer()
            }

        }
    }
}