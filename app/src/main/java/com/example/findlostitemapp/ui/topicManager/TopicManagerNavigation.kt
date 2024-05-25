package com.example.findlostitemapp.ui.topicManager

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.findlostitemapp.domain.model.NavRoute

object TopicManagerNavigation {
    val route = NavRoute("topicManager")

    fun addNavigation(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = route.path) {
            TopicManagerScreen()
        }
    }
}