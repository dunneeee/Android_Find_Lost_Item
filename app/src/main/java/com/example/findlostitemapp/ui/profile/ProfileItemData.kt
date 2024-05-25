package com.example.findlostitemapp.ui.profile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.findlostitemapp.ui.approvePost.ApprovePostNavigation
import com.example.findlostitemapp.ui.topicManager.TopicManagerNavigation

data class ProfileItemData(val represent: ProfileItem, val icon: ImageVector, val title: String) {
    enum class ProfileItem(val path: String) {
        Profile("profile"),
        Settings("settings"),
        Logout("logout"),
        ManagerUser("managerUser"),
        ManagerTopic(TopicManagerNavigation.route.path),
        ApprovePost(ApprovePostNavigation.route.path),
    }

    companion object {
        val adminItems = listOf(
            ProfileItemData(ProfileItem.ManagerUser, Icons.Filled.Person, "Quản lý người dùng"),
            ProfileItemData(ProfileItem.ManagerTopic, Icons.Filled.Edit, "Quản lý chủ đề"),
            ProfileItemData(ProfileItem.ApprovePost, Icons.Filled.Check, "Phê duyệt bài đăng")
        )
        val items = listOf(
            ProfileItemData(ProfileItem.Profile, Icons.Filled.Person, "Trang cá nhân"),
            ProfileItemData(ProfileItem.Settings, Icons.Filled.Settings, "Cài đặt"),
            ProfileItemData(ProfileItem.Logout, Icons.Filled.Lock, "Đăng xuất")
        )
    }
}
