package com.example.findlostitemapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.ui.theme.FindLostItemAppTheme

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment =
            Alignment
                .CenterHorizontally
        ) {
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(0.2f), thickness = 1.dp, modifier = Modifier
                    .padding(bottom = 20.dp)
            )
            Text(
                text = "Copy right 2024 FindLostItemApp. All rights reserved.",
                style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "@DUV Team", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview
@Composable
private fun FooterPreview() {
    FindLostItemAppTheme {
        Footer()
    }
}