package com.example.findlostitemapp.ui.userManager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.domain.model.User
import com.example.findlostitemapp.ui.components.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserManagerItem(
    modifier: Modifier = Modifier, user: User.Instance, onClick: (User.Instance) -> Unit = {},
    onDelete: (User.Instance) -> Unit = {}
) {
    ElevatedCard(
        onClick = {
            onClick(user)
        }, modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                User(user = user)
                Spacer(modifier = Modifier.weight(1f))
                UserTag(tag = user.getRole())
            }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onDelete(user) }, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(text = "Delete")
                }
            }
        }
    }
}

@Composable
fun UserTag(modifier: Modifier = Modifier, tag: String = "") {
    Card(modifier = modifier) {
        Text(text = tag, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
    }
}