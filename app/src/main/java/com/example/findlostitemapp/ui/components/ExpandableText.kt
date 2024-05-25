package com.example.findlostitemapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier, text: String = "", maxChars: Int = 200, style: TextStyle =
        TextStyle.Default
) {
    var isExpanded by remember { mutableStateOf(false) }

    if (text.length <= maxChars) {
        Text(text = text, modifier = modifier, style = style)
    } else {
        // Hiển thị văn bản cắt ngắn với dấu "..."
        val displayText = if (isExpanded) text else text.take(maxChars) + "..."

        Column {
            Text(text = displayText, modifier = modifier, style = style)
            Text(
                text = if (isExpanded) "Thu gọn" else "Xem thêm",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 8.dp)
                    .clickable { isExpanded = !isExpanded }
            )
        }
    }
}