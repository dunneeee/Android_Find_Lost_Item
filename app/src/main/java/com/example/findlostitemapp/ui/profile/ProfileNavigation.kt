package com.example.findlostitemapp.ui.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.findlostitemapp.domain.model.NavRoute

object ProfileNavigation {
    val route = object : NavRoute("profile") {}

    fun addNavigation(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = route.path) {
            ProfileScreen()
        }
    }
}