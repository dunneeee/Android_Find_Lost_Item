package com.example.findlostitemapp.hooks

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.findlostitemapp.api.UserServices
import com.example.findlostitemapp.domain.model.Http
import com.example.findlostitemapp.domain.model.User
import com.example.findlostitemapp.exceptions.ApiExceptions
import com.example.findlostitemapp.exceptions.AuthExceptions
import com.example.findlostitemapp.utils.FileUtils
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

data class UserCountState(
    val state: ApiState<Int>,
    val execute: () -> Unit
)

@Composable
fun rememberUserCountState(): UserCountState {
    val state = rememberApiState<Int>()
    val services = UserServices.getInstance(LocalContext.current)
    fun execute() {
        state.execute {
            try {
                val res = services.getUserCount()
                println(res.body())
                if (res.isSuccessful) {
                    val data = res.body()?.data ?: 0
                    data
                } else {
                    val error = Gson().fromJson<Http.Error<String>>(res.errorBody()?.string(), Http.Error::class.java)
                    when (error.code) {
                        else -> throw ApiExceptions.InternalServerError()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    return remember(state) {
        UserCountState(
            state = state,
            execute = ::execute
        )
    }
}

data class UserListState(
    val state: ApiState<List<User.Instance>>,
    val execute: (Int?, Int?) -> Unit
)

@Composable
fun rememberGetUsers(): UserListState {
    val state = rememberApiState<List<User.Instance>>()
    val services = UserServices.getInstance(LocalContext.current)
    fun execute(page: Int?, size: Int?) {
        state.execute {
            try {
                val res = services.getUsers(page, size)
                println(res.body())
                if (res.isSuccessful) {
                    val data = res.body()?.data ?: emptyList()
                    data
                } else {
                    val error = Gson().fromJson<Http.Error<String>>(res.errorBody()?.string(), Http.Error::class.java)
                    when (error.code) {
                        401 -> throw AuthExceptions.Unauthorized()
                        else -> throw ApiExceptions.InternalServerError()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    return remember(state) {
        UserListState(
            state = state,
            execute = ::execute
        )
    }
}


data class DeleteUserState(
    val state: ApiState<User.Instance>,
    val execute: (String) -> Unit
)

@Composable
fun rememberDeleteUserState(): DeleteUserState {
    val state = rememberApiState<User.Instance>()
    val services = UserServices.getInstance(LocalContext.current)
    fun execute(uuid: String) {
        state.execute {
            try {
                val res = services.removeUser(uuid)
                println(res.body())
                if (res.isSuccessful) {
                    val data = res.body()?.data!!
                    data
                } else {
                    val error = Gson().fromJson<Http.Error<String>>(res.errorBody()?.string(), Http.Error::class.java)
                    when (error.code) {
                        401 -> throw AuthExceptions.Unauthorized()
                        else -> throw ApiExceptions.InternalServerError()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    return remember(state) {
        DeleteUserState(
            state = state,
            execute = ::execute
        )
    }
}

data class AddUserState(
    val state: ApiState<User.Instance>,
    val execute: (User.Register) -> Unit
)

@Composable
fun rememberAddUserState(): AddUserState {
    val state = rememberApiState<User.Instance>()
    val services = UserServices.getInstance(LocalContext.current)
    fun execute(user: User.Register) {
        state.execute {
            try {
                val res = services.addUser(user.toMap(), user.getAvatar())
                println(res.body())
                if (res.isSuccessful) {
                    val data = res.body()?.data!!
                    data
                } else {
                    val error = Gson().fromJson<Http.Error<String>>(res.errorBody()?.string(), Http.Error::class.java)
                    when (error.code) {
                        401 -> throw AuthExceptions.Unauthorized()
                        409 -> throw AuthExceptions.UserAlreadyExists()
                        400 -> throw ApiExceptions.MissingParameter(error.error)
                        else -> throw ApiExceptions.InternalServerError()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    return remember(state) {
        AddUserState(
            state = state,
            execute = ::execute
        )
    }
}

data class ChangeUserAvatarState(
    val state: ApiState<User.Instance>,
    val execute: (Uri) -> Unit
)

@Composable
fun rememberChangeUserAvatarState(): ChangeUserAvatarState {
    val context = LocalContext.current
    val state = rememberApiState<User.Instance>()
    val services = UserServices.getInstance(LocalContext.current)
    fun execute(avatar: Uri) {
        val file = FileUtils.uriToFile(context, avatar, avatar.lastPathSegment)
            ?: File("")
        val avatarPart = MultipartBody.Part.createFormData(
            "avatar",
            file.name,
            file.asRequestBody("image/*".toMediaType())
        )
        state.execute {
            try {
                val res = services.changeAvatar(avatarPart)
                println(res.body())
                if (res.isSuccessful) {
                    val data = res.body()?.data!!
                    data
                } else {
                    val error = Gson().fromJson<Http.Error<String>>(res.errorBody()?.string(), Http.Error::class.java)
                    when (error.code) {
                        401 -> throw AuthExceptions.Unauthorized()
                        else -> throw ApiExceptions.InternalServerError()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    return remember(state) {
        ChangeUserAvatarState(
            state = state,
            execute = ::execute
        )
    }
}
