package com.example.findlostitemapp.hooks

import androidx.compose.runtime.*
import com.example.findlostitemapp.domain.model.NotificationArgs


class NotificationState {
    var notificationArgs by mutableStateOf(NotificationArgs())

    fun showNotification(args: NotificationArgs) {
        notificationArgs = args.copy(isOpen = true)
    }

    fun hideNotification() {
        notificationArgs = notificationArgs.copy(isOpen = false)
    }

    fun onConfirm() {
        notificationArgs.onConfirm?.invoke()
        hideNotification()
    }

    fun onDismiss() {
        notificationArgs.onDismiss?.invoke()
        hideNotification()
    }

    fun setContent(content: @Composable () -> Unit) {
        notificationArgs = notificationArgs.copy(content = content)
    }

    fun hasConfirm() = notificationArgs.onConfirm != null
    fun hasDismiss() = notificationArgs.onDismiss != null
    fun isOpen() = notificationArgs.isOpen
}

@Composable
fun rememberNotificationState(): NotificationState {
    return remember {
        NotificationState()
    }
}