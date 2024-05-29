package com.example.findlostitemapp.api

import android.content.Context
import com.example.findlostitemapp.domain.model.Http
import com.example.findlostitemapp.domain.model.SingleUserPayload
import com.example.findlostitemapp.domain.model.UserPayload
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface UserServices {
    @GET("public-users/count")
    suspend fun getUserCount(): Response<Http.Payload<Int>>

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int?, @Query("size") size: Int?): Response<UserPayload>

    @POST("users")
    @Multipart
    suspend fun addUser(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part avatar: MultipartBody.Part? = null
    ):
            Response<SingleUserPayload>

    @DELETE("users/{uuid}")
    suspend fun removeUser(@Path("uuid") uuid: String): Response<SingleUserPayload>

    @POST("profiles/me/avatar")
    @Multipart
    suspend fun changeAvatar(@Part avatar: MultipartBody.Part): Response<SingleUserPayload>

    companion object {
        fun getInstance(context: Context): UserServices = ApiClient.getClient(context).create(UserServices::class.java)
    }
}