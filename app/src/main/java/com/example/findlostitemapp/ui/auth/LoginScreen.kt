package com.example.findlostitemapp.ui.auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.findlostitemapp.domain.model.NotificationArgs
import com.example.findlostitemapp.domain.model.User
import com.example.findlostitemapp.navigation.LocalNavProvider
import com.example.findlostitemapp.providers.LocalNotification
import com.example.findlostitemapp.ui.components.AppLogo
import com.example.findlostitemapp.ui.components.CustomButton
import com.example.findlostitemapp.ui.components.CustomTextField
import com.example.findlostitemapp.ui.home.HomeNavigation
import com.example.findlostitemapp.hooks.rememberFormState
import com.example.findlostitemapp.hooks.rememberLoginState


@Composable
fun LoginScreen() {
    LoginContent(
        modifier = Modifier
            .fillMaxSize(),
    )
}

@Composable
fun LoginContent(modifier: Modifier = Modifier) {
    var openPasswordForget by remember { mutableStateOf(false) }

    val authStore = AuthLocalStore(LocalContext.current)
    val notificationState = LocalNotification.current
    val navigation = LocalNavProvider.current

    val loginState = rememberLoginState()

    val formState = rememberFormState(TextFieldValue("")) {
        it.text.isNotEmpty()
    }

    val handleRegisterClicked = {
        navigation.navigate(AuthNavigation.registerRoute.path)
    }

    val handleLoginClicked = {
        if (formState.isValid()) {
            val username = formState.getValue(AuthValidators.Keys.USERNAME).text
            val password = formState.getValue(AuthValidators.Keys.PASSWORD).text
            val userRequest = User.Login(username, password)
            loginState.execute(userRequest)
        }
    }

    val handleForgotPasswordClicked = {
        openPasswordForget = true
    }

    LaunchedEffect(loginState.state.type) {
        if (loginState.state.isSuccess) {
            authStore.saveToken(loginState.token)
            val user = loginState.state.data!!
            authStore.saveUser(user)
            navigation.navigate(HomeNavigation.route.path)
        }

        if (loginState.state.isError) {
            val error = loginState.state.error
            notificationState.showNotification(
                NotificationArgs(
                    title = "Đăng nhập thất bại",
                    content = {
                        Text(text = error?.message ?: "Đã có lỗi xảy ra")
                    },
                    dismissText = "Đóng"
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        formState.registerField(AuthValidators.loginValidator)
    }

    if (openPasswordForget) {
        ForgotPasswordBottom(
            onDismissRequest = { openPasswordForget = false },
            modifier = Modifier
                .fillMaxHeight()
        )
    }

    Surface(modifier) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                AppLogo()
            }
            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = "Đăng nhập",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                CustomTextField(value = formState.getValue(AuthValidators.Keys.USERNAME), onValueChange = {
                    formState.setValue(AuthValidators.Keys.USERNAME, it)
                }, label = "Tên đăng nhập", error = formState.getError(AuthValidators.Keys.USERNAME), leadingIcon = {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Account Icon")
                })
                CustomTextField(
                    value = formState.getValue(AuthValidators.Keys.PASSWORD),
                    onValueChange = {
                        formState.setValue(AuthValidators.Keys.PASSWORD, it)
                    },
                    label = "Mật khẩu",
                    isPassword = true,
                    error = formState.getError(AuthValidators.Keys.PASSWORD),
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Account Icon")
                    },
                    isLasted = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomButton(
                    onClick = handleLoginClicked,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = formState.isValid() && !loginState.state.isLoading,
                ) {
                    Text(text = "Đăng nhập")
                }
                TextButton(
                    onClick = handleForgotPasswordClicked,
                    Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Quên mật khẩu?", color = MaterialTheme.colorScheme.primary)
                }
            }

            OutlinedButton(onClick = handleRegisterClicked, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Đăng ký tài khoản mới")
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordBottom(modifier: Modifier = Modifier, onDismissRequest: () -> Unit) {
    val formState = rememberFormState(TextFieldValue("")) {
        it.text.isNotEmpty()
    }
    val emailValidator = remember {
        AuthValidators.registerValidator.first { it.key == "email" }
    }

    LaunchedEffect(Unit) {
        formState.registerField(emailValidator)
    }

    ModalBottomSheet(onDismissRequest = onDismissRequest, modifier = modifier, dragHandle = {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment
                .CenterHorizontally
        ) {
            BottomSheetDefaults.DragHandle()
            Text(text = "Quên mật khẩu?", style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp))
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
        }
    }) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            CustomTextField(
                value = formState.getValue("email"),
                onValueChange = {
                    formState.setValue("email", it)
                },
                label = "Email",
                error = formState.getError("email"),
                leadingIcon = {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Account Icon")
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = formState.isValid()
            ) {
                Text(text = "Gửi yêu cầu")
            }
        }
    }
}