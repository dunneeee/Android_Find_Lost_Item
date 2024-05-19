package com.example.findlostitemapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.findlostitemapp.R
import com.example.findlostitemapp.domain.model.User
import com.example.findlostitemapp.ui.theme.FindLostItemAppTheme
import com.example.findlostitemapp.utils.FakeData

enum class UserAvatarSize(val value: Dp) {
    Small(32.dp),
    Medium(48.dp),
    Large(64.dp)
}

@Composable
fun User(
    modifier: Modifier = Modifier,
    user: User.Instance,
    avatarSize: UserAvatarSize = UserAvatarSize.Medium,
    onClick: () -> Unit = {}
) {

    val textStyle = when (avatarSize) {
        UserAvatarSize.Small -> MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp)
        UserAvatarSize.Medium -> MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
        UserAvatarSize.Large -> MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
    }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(avatarSize.value)
                .clip(CircleShape).clickable { onClick() },

        ) {
            AsyncImage(
                model = user.avatar,
                contentDescription = user.username,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = user.username, modifier = Modifier.clickable {
            onClick()
        }, style = textStyle)
    }
}

@Preview
@Composable
private fun UserPreview() {
    FindLostItemAppTheme {
        User(user = FakeData.getRandUser())
    }
}