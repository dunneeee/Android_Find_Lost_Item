package com.example.findlostitemapp.providers

import androidx.compose.runtime.Composable

@Composable
fun StateProvider(content: @Composable () -> Unit) {
    NotificationProvider {
        content()
    }
}