package com.example.findlostitemapp.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.findlostitemapp.ui.home.HomeNavigation
import com.example.findlostitemapp.ui.posts.PostsNavigation
import com.example.findlostitemapp.ui.profile.ProfileNavigation
import com.example.findlostitemapp.ui.uploadPost.UploadPostNavigation

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {

    data object Home : BottomNavItem(HomeNavigation.route.path, Icons.Default.Home, "Trang chủ")

    data object UploadPost : BottomNavItem(UploadPostNavigation.route.path, Icons.Default.Create, "Đăng bài")

    data object Profile : BottomNavItem(ProfileNavigation.route.path, Icons.Default.Person, "Hồ sơ")

    data object Posts : BottomNavItem(PostsNavigation.route.withQueryParamsFormat(), Icons.Default.Person, "Bài đăng")

    companion object {
        fun fromRoute(route: String): BottomNavItem {
            return when (route) {
                Home.route -> Home
                UploadPost.route -> UploadPost
                Profile.route -> Profile
                else -> Home
            }
        }

        fun allItems(): List<BottomNavItem> {
            return listOf(Home, Posts, UploadPost, Profile)
        }
    }
}
