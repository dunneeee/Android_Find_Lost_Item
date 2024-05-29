package com.example.findlostitemapp.ui.userManager

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.findlostitemapp.domain.model.NavRoute

object UserManagerNavigation {
    val route = NavRoute("userManager")

    fun addNavigation(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = route.path) {
            UserManagerScreen()
        }
    }
}