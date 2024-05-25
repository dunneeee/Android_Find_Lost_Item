package com.example.findlostitemapp.ui.topicManager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.domain.model.Post


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicManagerItem(
    modifier: Modifier = Modifier, topic: Post.Topic, onCardClick: () -> Unit = {}, onDeleteClick: () ->
    Unit = {}
) {
    ElevatedCard(onClick = onCardClick, modifier = modifier) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = topic.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = topic.uuid, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = onDeleteClick) {
                Text(
                    text = "Xóa", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography
                        .bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}