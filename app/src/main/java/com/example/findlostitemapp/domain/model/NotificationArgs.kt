package com.example.findlostitemapp.domain.model

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class NotificationArgs(
    val title: String = "",
    val onConfirm: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null,
    val isOpen: Boolean = false,
    val content: @Composable () -> Unit = {},
    val modifier: Modifier = Modifier,
    val onClose: (() -> Unit)? = null,
    val dismissText: String = "Hủy",
    val confirmText: String = "Xác nhận",
)
