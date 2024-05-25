package com.example.findlostitemapp.ui.approvePost

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.findlostitemapp.domain.model.NavRoute

object ApprovePostNavigation {
    val route = NavRoute("approvePost")

    fun addNavigation(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = route.path) {
            ApprovePostScreen()
        }
    }
}