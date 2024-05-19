package com.example.findlostitemapp.ui.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

enum class ButtonType {
    OUTLINE,
    PRIMARY,
    TEXT,
    ICON
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier, type: ButtonType = ButtonType.PRIMARY, onClick: () -> Unit,
    enabled: Boolean = false,
    loading: Boolean = false,
    content:
    @Composable () -> Unit
) {

    @Composable
    fun innerContent() {
        content()
    }

    when (type) {
        ButtonType.OUTLINE -> {
            OutlinedButton(
                modifier = modifier,
                onClick = onClick,
                enabled = enabled,
                content = { innerContent() }
            )
        }

        ButtonType.PRIMARY -> {
            Button(
                modifier = modifier,
                onClick = onClick,
                enabled = enabled,
                content = { innerContent() }
            )
        }

        ButtonType.TEXT -> {
            TextButton(
                modifier = modifier,
                onClick = onClick,
                enabled = enabled,
                content = { innerContent() }
            )
        }

        ButtonType.ICON -> {
            IconButton(
                modifier = modifier,
                onClick = onClick,
                enabled = enabled,
                content = { innerContent() }
            )
        }
    }

}