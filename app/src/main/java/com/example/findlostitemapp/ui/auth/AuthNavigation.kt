package com.example.findlostitemapp.ui.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.findlostitemapp.domain.model.NavRoute

object AuthNavigation {
    class LoginRoute : NavRoute("login")
    class RegisterRoute : NavRoute("register")
    class UploadAvatarRoute : NavRoute("upload-avatar")

    val loginRoute = LoginRoute()
    val registerRoute = RegisterRoute()
    val uploadAvatarRoute = UploadAvatarRoute()

    fun addNavigation(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = loginRoute.path) {
            LoginScreen()
        }

        navGraphBuilder.composable(route = registerRoute.path) {
            RegisterScreen()
        }

        navGraphBuilder.composable(route = uploadAvatarRoute.path) {
            AvatarUploadScreen()
        }
    }
}