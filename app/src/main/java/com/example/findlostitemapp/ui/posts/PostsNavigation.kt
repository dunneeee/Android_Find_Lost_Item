package com.example.findlostitemapp.ui.posts

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.findlostitemapp.domain.model.NavRoute
import com.example.findlostitemapp.domain.model.SearchData

object PostsNavigation {
    class Route : NavRoute("posts") {
        fun withQueryParamsFormat(): String {
            return buildQueryArgsFormat("item", "location", "topic")
        }

        fun withQueryParams(item: String = "", location: String = "", topic: String = ""): String {
            return buildQueryArgs(Pair("item", item), Pair("location", location), Pair("topic", topic))
        }
    }
    val route = Route()


    fun addNavigation(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = route.withQueryParamsFormat(), arguments = listOf(
            navArgument("item") {
                defaultValue = ""
                type = NavType.StringType
            },
            navArgument("location") {
                defaultValue = ""
                type = NavType.StringType
            },
            navArgument("topic") {
                defaultValue = ""
                type = NavType.StringType
            }
        )) {
            backStackEntry ->
            val args = backStackEntry.arguments
            val item = args?.getString("item") ?: ""
            val location = args?.getString("location") ?: ""
            val topic = args?.getString("topic") ?: ""
            val searchQuery = SearchData(item, location, topic)
            PostsScreen(searchQuery = searchQuery)
        }
    }
}