package com.example.findlostitemapp.ui.postDetail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.findlostitemapp.domain.model.NavRoute

object PostDetailNavigation {
    class PostDetailRoute : NavRoute("postDetail") {
        fun withArgsFormat() = buildArgsFormat("postId")
        fun withArgs(postId: String) = buildArgs(postId)
    }
    val route = PostDetailRoute()

    fun addNavigation(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = route.withArgsFormat()) {
            backStackEntry ->
            val args = backStackEntry.arguments
            val postId = args?.getString("postId", "") ?: ""
            PostDetailScreen(postId = postId)
        }
    }
}