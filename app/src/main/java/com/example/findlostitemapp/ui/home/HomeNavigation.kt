package com.example.findlostitemapp.ui.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.findlostitemapp.domain.model.NavRoute


object HomeNavigation {
    val route = NavRoute("home")
    fun addNavigation(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = route.path) {
            HomeScreen()
        }
    }
}