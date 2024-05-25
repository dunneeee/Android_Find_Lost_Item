package com.example.findlostitemapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.findlostitemapp.ui.approvePost.ApprovePostNavigation
import com.example.findlostitemapp.ui.auth.AuthNavigation
import com.example.findlostitemapp.ui.home.HomeNavigation
import com.example.findlostitemapp.ui.postDetail.PostDetailNavigation
import com.example.findlostitemapp.ui.posts.PostsNavigation
import com.example.findlostitemapp.ui.profile.ProfileNavigation
import com.example.findlostitemapp.ui.topicManager.TopicManagerNavigation
import com.example.findlostitemapp.ui.uploadPost.UploadPostNavigation

@Composable
fun NavGraph(modifier: Modifier, navController: NavHostController) {
    NavHost(navController = navController, startDestination = ApprovePostNavigation.route.path, modifier = modifier) {
        HomeNavigation.addNavigation(this)
        PostsNavigation.addNavigation(this)
        ProfileNavigation.addNavigation(this)
        UploadPostNavigation.addNavigation(this)
        AuthNavigation.addNavigation(this)
        PostDetailNavigation.addNavigation(this)
        TopicManagerNavigation.addNavigation(this)
        ApprovePostNavigation.addNavigation(this)
    }
}