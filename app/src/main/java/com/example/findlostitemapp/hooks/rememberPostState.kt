package com.example.findlostitemapp.hooks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.findlostitemapp.api.PostServices
import com.example.findlostitemapp.domain.model.Http
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.domain.model.SearchData
import com.example.findlostitemapp.exceptions.ApiExceptions
import com.example.findlostitemapp.exceptions.AuthExceptions
import com.example.findlostitemapp.exceptions.PostExceptions
import com.example.findlostitemapp.utils.FileUtils
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


data class PostState(val state: ApiState<List<Post.Instance>>, val execute: (Int?, Int?) -> Unit)

@Composable
fun rememberRecommendedPostState(): PostState {
    val state = rememberApiState<List<Post.Instance>>()
    val services = PostServices.getInstance(LocalContext.current)
    fun execute(page: Int?, limit: Int?) {
        state.execute {
            try {
                val res = services.getRecommendedPosts(page ?: 1, limit ?: 10)
                if (res.isSuccessful) {
                    res.body()?.data ?: emptyList()
                } else {
                    throw ApiExceptions.InternalServerError()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw ApiExceptions.InternalServerError()
            }
        }
    }

    return remember {
        PostState(
            state = state,
            execute = ::execute
        )
    }
}

data class PostCountState(
    val state: ApiState<Int>,
    val execute: () -> Unit
)

@Composable
fun rememberPostCountState(): PostCountState {
    val state = rememberApiState<Int>()
    val services = PostServices.getInstance(LocalContext.current)
    fun execute() {
        state.execute {
            try {
                val res = services.getPostCount()
                if (res.isSuccessful) {
                    res.body()?.data ?: 0
                } else {
                    throw ApiExceptions.InternalServerError()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw ApiExceptions.InternalServerError()
            }
        }
    }

    return remember {
        PostCountState(
            state = state,
            execute = ::execute
        )
    }
}

data class PostByIdState(
    val state: ApiState<Post.Instance>,
    val execute: (String) -> Unit
)

@Composable
fun rememberPostByIdState(): PostByIdState {
    val state = rememberApiState<Post.Instance>()
    val services = PostServices.getInstance(LocalContext.current)
    fun execute(id: String) {
        state.execute {
            try {
                val res = services.getPostById(id)
                if (res.isSuccessful) {
                    res.body()?.data ?: throw PostExceptions.PostNotFoundException()
                } else {
                    val error = Gson().fromJson<Http.Error<String>>(res.errorBody()?.string(), Http.Error::class.java)
                    when (error.code) {
                        404 -> throw PostExceptions.PostNotFoundException()
                        403 -> throw PostExceptions.CantAccessPostException()
                        else -> throw ApiExceptions.InternalServerError()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    return remember {
        PostByIdState(
            state = state,
            execute = ::execute
        )
    }
}

data class UploadPostState(
    val state: ApiState<Post.Instance>,
    val execute: (Post.Upload) -> Unit
)

@Composable
fun rememberUploadPostState(): UploadPostState {
    val context = LocalContext.current
    val state = rememberApiState<Post.Instance>()
    val services = PostServices.getInstance(LocalContext.current)
    fun execute(upload: Post.Upload) {
        val images = upload.images
        state.execute {
            try {
                val res = services.addPost(
                    upload.toMap(),
                    images.map {
                        val file = FileUtils.uriToFile(context, it, it.lastPathSegment)
                            ?: File("")
                        MultipartBody.Part.createFormData(
                            "images[]",
                            file.name,
                            file.asRequestBody("image/*".toMediaType())
                        )
                    }
                )
                println(res)
                if (res.isSuccessful) {
                    res.body()?.data ?: throw PostExceptions.PostNotFoundException()
                } else {
                    val error = Gson().fromJson<Http.Error<String>>(res.errorBody()?.string(), Http.Error::class.java)
                    when (error.code) {
                        400 -> throw PostExceptions.MissingFieldException(error.error)
                        401 -> throw AuthExceptions.Unauthorized()
                        404 -> throw PostExceptions.PostNotFoundException()
                        403 -> throw PostExceptions.CantAccessPostException()
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
        UploadPostState(
            state = state,
            execute = ::execute
        )
    }
}

data class SearchPostState(
    val state: ApiState<List<Post.Instance>>,
    val execute: (SearchData) -> Unit
)

@Composable
fun rememberSearchPostState(): SearchPostState {
    val state = rememberApiState<List<Post.Instance>>()
    val services = PostServices.getInstance(LocalContext.current)
    fun execute(searchData: SearchData) {

        state.execute {
            try {
                val res = services.searchPosts(searchData.toMap())
                if (res.isSuccessful) {
                    res.body()?.data ?: emptyList()
                } else {
                    throw ApiExceptions.InternalServerError()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    return remember(state) {
        SearchPostState(
            state = state,
            execute = ::execute
        )
    }
}

data class ChangeStatusPostState(
    val state: ApiState<Post.Instance>,
    val acceptPost: (String) -> Unit,
    val rejectPost: (String) -> Unit
)

@Composable
fun rememberChangeStatusPostState(): ChangeStatusPostState {
    val state = rememberApiState<Post.Instance>()
    val services = PostServices.getInstance(LocalContext.current)

    fun acceptPost(uuid: String) {
        state.execute {
            try {
                val res = services.acceptPost(uuid)
                if (res.isSuccessful) {
                    res.body()?.data ?: throw PostExceptions.PostNotFoundException()
                } else {
                    val error = Gson().fromJson<Http.Error<String>>(res.errorBody()?.string(), Http.Error::class.java)
                    when (error.code) {
                        401 -> throw AuthExceptions.Unauthorized()
                        404 -> throw PostExceptions.PostNotFoundException()
                        403 -> throw PostExceptions.CantAccessPostException()
                        else -> throw ApiExceptions.InternalServerError()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    fun rejectPost(uuid: String) {
        state.execute {
            try {
                val res = services.rejectPost(uuid)
                if (res.isSuccessful) {
                    res.body()?.data ?: throw PostExceptions.PostNotFoundException()
                } else {
                    val error = Gson().fromJson<Http.Error<String>>(res.errorBody()?.string(), Http.Error::class.java)
                    when (error.code) {
                        401 -> throw AuthExceptions.Unauthorized()
                        404 -> throw PostExceptions.PostNotFoundException()
                        403 -> throw PostExceptions.CantAccessPostException()
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
        ChangeStatusPostState(
            state = state,
            acceptPost = ::acceptPost,
            rejectPost = ::rejectPost
        )
    }
}

data class GetPendingPostState(
    val state: ApiState<List<Post.Instance>>,
    val execute: () -> Unit
)

@Composable
fun rememberGetPendingPostState(): GetPendingPostState {
    val state = rememberApiState<List<Post.Instance>>()
    val services = PostServices.getInstance(LocalContext.current)
    fun execute() {
        state.execute {
            try {
                val res = services.getPendingPosts()
                if (res.isSuccessful) {
                    res.body()?.data ?: emptyList()
                } else {
                    throw ApiExceptions.InternalServerError()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    return remember(state) {
        GetPendingPostState(
            state = state,
            execute = ::execute
        )
    }
}