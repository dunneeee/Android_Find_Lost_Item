package com.example.findlostitemapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavProvider = staticCompositionLocalOf<NavHostController> {
    error("No NavController provided")
}

@Composable
fun NavProvider(navController: NavHostController, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalNavProvider provides navController) {
        content()
    }
}
