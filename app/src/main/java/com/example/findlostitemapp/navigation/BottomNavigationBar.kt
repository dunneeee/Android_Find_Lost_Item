package com.example.findlostitemapp.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

import com.example.findlostitemapp.domain.model.BottomNavItem
import com.example.findlostitemapp.ui.auth.AuthNavigation

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val ignoreRoutes = listOf(null, AuthNavigation.loginRoute.path, AuthNavigation.registerRoute.path)

    if (ignoreRoutes.contains(currentRoute)) {
        return
    }

    NavigationBar(modifier = modifier) {
        BottomNavItem.allItems().forEach { navItem ->
            val isCurrentRoute = currentRoute == navItem.route
            NavigationBarItem(selected = isCurrentRoute, onClick = {
                if(!isCurrentRoute) {
                    navController.navigate(navItem.route)
                }
            }, icon = {
                Icon(navItem.icon, contentDescription = navItem.label)
            }, label = {
                Text(text = navItem.label)
            })
        }
    }
}
