package com.example.findlostitemapp.providers

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.findlostitemapp.domain.model.NotificationArgs
import com.example.findlostitemapp.hooks.NotificationState
import com.example.findlostitemapp.hooks.rememberNotificationState


val LocalNotification = compositionLocalOf<NotificationState> {
    error("No NotificationProvider provided")
}

@Composable
fun NotificationProvider(content: @Composable () -> Unit) {
    val state = rememberNotificationState()

    CompositionLocalProvider(LocalNotification provides state) {
        if (state.isOpen()) {
            AlertDialog(onDismissRequest = {
                state.hideNotification()
            }, confirmButton = {
                if (state.hasConfirm()) {
                    TextButton(onClick = { state.onConfirm() }) {
                        Text(
                            text = state.notificationArgs.confirmText, style = MaterialTheme.typography.bodyMedium
                                .copy(color = MaterialTheme.colorScheme.onSurface)
                        )
                    }
                }
            }, dismissButton = {
                TextButton(onClick = { state.onDismiss() }) {
                    Text(
                        text = state.notificationArgs.dismissText, style = MaterialTheme.typography.bodyMedium
                            .copy(color = MaterialTheme.colorScheme.primary)
                    )
                }
            }, title = {
                Text(text = state.notificationArgs.title)
            }, text = {
                state.notificationArgs.content()
            })

        }
        content()
    }
}

