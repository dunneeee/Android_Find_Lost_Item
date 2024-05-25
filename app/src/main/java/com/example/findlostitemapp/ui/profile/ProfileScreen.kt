package com.example.findlostitemapp.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.navigation.LocalNavProvider
import com.example.findlostitemapp.ui.approvePost.ApprovePostNavigation
import com.example.findlostitemapp.ui.auth.AuthLocalStore
import com.example.findlostitemapp.ui.auth.AuthNavigation
import com.example.findlostitemapp.ui.components.User
import com.example.findlostitemapp.ui.components.UserAvatarSize
import com.example.findlostitemapp.ui.home.HomeNavigation
import com.example.findlostitemapp.ui.topicManager.TopicManagerNavigation

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val authStorage = AuthLocalStore(context)
    val navigation = LocalNavProvider.current
    if (!authStorage.isLoggedIn()) {
        navigation.navigate(AuthNavigation.loginRoute.path) {
            popUpTo(HomeNavigation.route.path)
        }
    }
    Scaffold(modifier = modifier, topBar = {
        ProfileTopBar()
    }) { paddingValues ->
        ProfileContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}


@Composable
fun ProfileContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val authStorage = AuthLocalStore(context)
    val user = remember {
        authStorage.getUser()
    }

    val items = remember {
        val list = mutableListOf<ProfileItemData>()
        if(user != null && user.isAdmin) {
            list.addAll(ProfileItemData.adminItems)
        }
        list.addAll(ProfileItemData.items)
        list
    }

    val navigation = LocalNavProvider.current

    val handleProfileItemClick = { item: ProfileItemData ->
        when (item.represent) {
            ProfileItemData.ProfileItem.ManagerTopic ->
                navigation.navigate(TopicManagerNavigation.route.path)
            ProfileItemData.ProfileItem.ApprovePost ->
                navigation.navigate(ApprovePostNavigation.route.path)
            else -> {
                println("Unknown item clicked")
            }
        }
    }

    LazyColumn(modifier = modifier) {
        item {
            if (user != null) User(user = user, avatarSize = UserAvatarSize.Large, modifier = Modifier.padding(16.dp))
        }

        items(items.size) { index ->
            val item = items[index]
            ProfileItem(
                title = item.title,
                leadingIcon = {
                    Icon(
                        item.icon, contentDescription = item.title, modifier = Modifier
                            .padding(start = 16.dp)
                            .size(28.dp)

                    )
                },
                divider = index != items.size - 1,
                onClick = { handleProfileItemClick(item) }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(modifier: Modifier = Modifier) {
    TopAppBar(modifier = modifier, title = { Text(text = "Profile") })
}

@Composable
fun ProfileItem(
    modifier: Modifier = Modifier, leadingIcon: @Composable () -> Unit, title: String, divider: Boolean =
        true,
    onClick: ()
    -> Unit = {}
) {
    Surface(modifier, shape = MaterialTheme.shapes.medium) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                leadingIcon()
                Text(text = title, modifier = Modifier.padding(16.dp))
            }
            if (divider) {
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
            }
        }
    }
}