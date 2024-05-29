package com.example.findlostitemapp.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
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
import com.example.findlostitemapp.hooks.rememberRegisterState
import java.io.File

@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {
    RegisterContent(
        modifier = modifier
            .fillMaxSize()
    )
}

@Composable
fun RegisterContent(modifier: Modifier = Modifier) {
    val formState = rememberFormState(TextFieldValue(""))
    {
        it.text.isNotEmpty()
    }
    val registerState = rememberRegisterState()
    val loginState = rememberLoginState()
    val authStore = AuthLocalStore(LocalContext.current)
    val notifyState = LocalNotification.current
    val navigation = LocalNavProvider.current

    val handleLoginClicked = {
        navigation.navigate(AuthNavigation.loginRoute.path) {
            popUpTo(HomeNavigation.route.path)
        }
    }

    val isEnable = remember {
        derivedStateOf {
            formState.isValid() && registerState.state.isLoading.not()
        }
    }

    val handleRegisterClicked = {
        if (formState.isValid()) {
            val username = formState.getValue(AuthValidators.Keys.USERNAME).text
            val email = formState.getValue(AuthValidators.Keys.EMAIL).text
            val password = formState.getValue(AuthValidators.Keys.PASSWORD).text
            val userRequest = User.Register(username, email, password, avatar = File(""))
            registerState.execute(userRequest)
        }
    }


    LaunchedEffect(registerState.state.type) {
        if (registerState.state.isSuccess) {
            val username = formState.getValue(AuthValidators.Keys.USERNAME).text
            val password = formState.getValue(AuthValidators.Keys.PASSWORD).text
            loginState.execute(User.Login(username, password))
            navigation.navigate(AuthNavigation.uploadAvatarRoute.path) {
                popUpTo(HomeNavigation.route.path) {
                    inclusive = true
                }
            }
        }

        if (registerState.state.isError) {
            val error = registerState.state.error!!
            notifyState.showNotification(
                NotificationArgs(
                    title = "Có lỗi xảy ra",
                    content = {
                        error.message?.let { Text(text = it) }
                    },
                    dismissText = "Thử lại"
                )
            )
        }
    }

    LaunchedEffect(loginState.state.type) {
        if (loginState.state.isError) {
            val error = loginState.state.error
            notifyState.showNotification(
                NotificationArgs(
                    title = "Đăng nhập thất bại",
                    content = {
                        Text(text = error?.message ?: "Đã có lỗi xảy ra")
                    },
                    dismissText = "Đóng"
                )
            )
        }

        if (loginState.state.isSuccess) {
            authStore.saveToken(loginState.token)
            val user = loginState.state.data!!
            authStore.saveUser(user)
            navigation.navigate(AuthNavigation.uploadAvatarRoute.path)
        }
    }

    LaunchedEffect(Unit) {
        formState.registerField(AuthValidators.registerValidator)
    }
    if (loginState.state.isLoading) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppLogo()
            }
        }
    } else
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
                        text = "Đăng ký",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    CustomTextField(
                        value = formState.getValue(AuthValidators.Keys.USERNAME), onValueChange = {
                            formState.setValue(AuthValidators.Keys.USERNAME, it)
                        }, label = "Tên" +
                                " đăng " +
                                "nhập",
                        error = formState.getError(AuthValidators.Keys.USERNAME),
                        leadingIcon = {
                            Icon(Icons.Default.AccountCircle, contentDescription = "Account Icon")
                        }
                    )
                    CustomTextField(
                        value = formState.getValue(AuthValidators.Keys.EMAIL), onValueChange = {
                            formState.setValue(AuthValidators.Keys.EMAIL, it)
                        },
                        label = "Email", error = formState.getError(AuthValidators.Keys.EMAIL),
                        leadingIcon = {
                            Icon(Icons.Default.MailOutline, contentDescription = "Account Icon")
                        }
                    )
                    CustomTextField(
                        value = formState.getValue(AuthValidators.Keys.PASSWORD),
                        onValueChange = {
                            formState.setValue(
                                AuthValidators.Keys.PASSWORD,
                                it
                            )
                        },
                        label = "Mật khẩu",
                        isPassword = true,
                        error = formState.getError(AuthValidators.Keys.PASSWORD),
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = "Account Icon")
                        }
                    )
                    CustomTextField(
                        value = formState.getValue(AuthValidators.Keys.CONFIRM_PASSWORD),
                        onValueChange = {
                            formState.setValue(
                                AuthValidators.Keys.CONFIRM_PASSWORD,
                                it
                            )
                        },
                        label = "Nhập lại mật khẩu",
                        isPassword = true,
                        error = formState.getError(AuthValidators.Keys.CONFIRM_PASSWORD),
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = "Account Icon")
                        },
                        isLasted = true

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomButton(
                        onClick = handleRegisterClicked,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = isEnable.value
                    ) {
                        Text(text = "Đăng ký")
                    }
                }

                OutlinedButton(onClick = handleLoginClicked, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Đã có tài khoản? Đăng nhập")
                }

            }
        }
}