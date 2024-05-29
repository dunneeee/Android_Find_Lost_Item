package com.example.findlostitemapp.ui.userManager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.LocalSnackBar
import com.example.findlostitemapp.domain.model.NotificationArgs
import com.example.findlostitemapp.domain.model.User
import com.example.findlostitemapp.hooks.rememberAddUserState
import com.example.findlostitemapp.hooks.rememberDeleteUserState
import com.example.findlostitemapp.hooks.rememberGetUsers
import com.example.findlostitemapp.providers.LocalNotification
import com.example.findlostitemapp.ui.MainLayout
import com.example.findlostitemapp.ui.components.DataNotFound
import kotlinx.coroutines.launch

@Composable
fun UserManagerScreen(modifier: Modifier = Modifier) {
    MainLayout(topBar = { UserManagerTopBar() }, modifier = modifier) {
        UserManagerContent()
    }
}

@Composable
fun UserManagerContent(modifier: Modifier = Modifier) {
    val snackBarState = LocalSnackBar.current
    val notifyState = LocalNotification.current
    val getUserState = rememberGetUsers()
    val deleteUserState = rememberDeleteUserState()
    val addUserState = rememberAddUserState()


    var openModifierUser by remember {
        mutableStateOf(false)
    }

    var userWasEdit by remember {
        mutableStateOf<User.Instance?>(null)
    }

    val handleClickAddBtn = {
        userWasEdit = null
        openModifierUser = true
    }

    val handleUserItemClick = { user: User.Instance ->
        userWasEdit = user
        openModifierUser = true
    }

    val handleDeleteBtnClick = { user: User.Instance ->
        notifyState.showNotification(NotificationArgs(
            title = "Xác nhận xóa",
            content = {
                Text(text = "Bạn có chắc chắn muốn xóa người dùng ${user.username} không?")
            },
            onConfirm = {
                deleteUserState.execute(user.uuid)
            }
        ))
    }

    val handleConfirmUserModifier = { user: User.Register ->
        if (userWasEdit == null) {
            addUserState.execute(user)
        }
    }

    LaunchedEffect(Unit) {
        getUserState.execute(null, null)
    }

    LaunchedEffect(getUserState.state.type) {
        if (getUserState.state.isError) {
            snackBarState.showSnackbar(
                message = getUserState.state.error?.message ?: "Có lỗi xảy ra khi lấy danh sách người dùng"
            )
        }

        if (getUserState.state.isSuccess) {
            println(getUserState.state.data)
        }
    }

    LaunchedEffect(deleteUserState.state.type) {
        if (deleteUserState.state.isError) {
            snackBarState.showSnackbar(
                message = deleteUserState.state.error?.message ?: "Có lỗi xảy ra khi xóa người dùng"
            )
        }

        if (deleteUserState.state.isSuccess) {
            launch {
                val user = deleteUserState.state.data!!
                getUserState.state.data = getUserState.state.data?.filter { it.uuid != user.uuid }
            }
            snackBarState.showSnackbar(
                message = "Xóa người dùng thành công"
            )
        }
    }

    LaunchedEffect(addUserState.state.type) {
        if (addUserState.state.isError) {
            snackBarState.showSnackbar(
                message = addUserState.state.error?.message ?: "Có lỗi xảy ra khi thêm người dùng"
            )
        }

        if (addUserState.state.isSuccess) {
            launch {
                val user = addUserState.state.data!!
                getUserState.state.data = getUserState.state.data?.toMutableList()?.apply {
                    add(user)
                }
            }
            snackBarState.showSnackbar(
                message = "Thêm người dùng thành công"
            )
        }
    }

    UserManagerModifier(open = openModifierUser, onDismiss = {
        openModifierUser = false
    }, user = userWasEdit, onConfirm = handleConfirmUserModifier)

    Column(modifier = modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16
                        .dp
                )
        ) {
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = handleClickAddBtn, shape = RoundedCornerShape(8.dp)) {
                    Text(text = "Thêm người dùng")
                }
            }
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }


        Spacer(modifier = Modifier.height(16.dp))
        if (getUserState.state.data != null) {
            if (getUserState.state.data!!.isEmpty()) {
                DataNotFound(
                    text = "Không có người dùng nào",
                )
            }
            getUserState.state.data?.forEach {
                UserManagerItem(user = it, onClick = handleUserItemClick, onDelete = handleDeleteBtnClick)
            }
        }

        if (getUserState.state.isLoading) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserManagerTopBar(modifier: Modifier = Modifier) {
    TopAppBar(title = {
        Text(text = "Quản lý người dùng")
    }, modifier = modifier)
}