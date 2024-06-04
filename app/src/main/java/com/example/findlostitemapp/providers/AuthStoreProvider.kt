package com.example.findlostitemapp.providers

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.example.findlostitemapp.domain.model.User
import com.example.findlostitemapp.ui.auth.AuthLocalStore


data class AuthState(
    val user: User.Instance? = null,
    val token: String? = null
)

sealed class AuthAction {
    data class ChangeAvatar(val avatar: String) : AuthAction()
    data class SetUser(val user: User.Instance) : AuthAction()
    data class SetToken(val token: String) : AuthAction()
    data class LoginSuccess(val user: User.Instance, val token: String) : AuthAction()
    data object Logout : AuthAction()

}
typealias AuthReducer = (AuthState, AuthAction) -> AuthState

class AuthStore(initialState: AuthState, private val reducer: AuthReducer) {
    private var state: AuthState = initialState

    fun dispatch(action: AuthAction) {
        state = reducer(state, action)
    }

    fun getState(): AuthState {
        return state
    }
}

val LocalAuthStore = compositionLocalOf<AuthStore> {
    error("No AuthStoreProvider provided")
}

@Composable
fun AuthStoreProvider(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val authLocalStore = AuthLocalStore(context)
    val initialState = AuthState(
        user = authLocalStore.getUser(),
        token = authLocalStore.getToken()
    )

    val reducer: AuthReducer = { state, action ->
        when (action) {
            is AuthAction.ChangeAvatar -> {
                authLocalStore.saveUser(state.user!!.copy(avatar = action.avatar))
                state.copy(user = state.user.copy(avatar = action.avatar))
            }

            is AuthAction.SetUser -> {
                authLocalStore.saveUser(action.user)
                state.copy(user = action.user)
            }

            is AuthAction.SetToken -> {
                authLocalStore.saveToken(action.token)
                state.copy(token = action.token)
            }

            is AuthAction.LoginSuccess -> {
                authLocalStore.saveUser(action.user)
                authLocalStore.saveToken(action.token)
                state.copy(user = action.user, token = action.token)
            }

            is AuthAction.Logout -> {
                authLocalStore.clear()
                state.copy(user = null, token = null)
            }
        }
    }
    val authStore = AuthStore(initialState, reducer)
    LaunchedEffect(Unit) {
        val user = authLocalStore.getUser()
        val token = authLocalStore.getToken()
        if (user != null && token != null) {
            authStore.dispatch(AuthAction.LoginSuccess(user, token))
        }
    }

    CompositionLocalProvider(LocalAuthStore provides authStore) {
        content()
    }
}