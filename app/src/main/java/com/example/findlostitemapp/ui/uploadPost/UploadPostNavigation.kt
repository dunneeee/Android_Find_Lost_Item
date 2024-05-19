package com.example.findlostitemapp.ui.uploadPost

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.findlostitemapp.domain.model.NavRoute


object UploadPostNavigation {
    val route = object : NavRoute("uploadPost") {}

    fun addNavigation(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = route.path) {
            UploadPostScreen()
        }
    }
}
