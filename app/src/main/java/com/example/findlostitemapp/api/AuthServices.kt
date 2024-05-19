package com.example.findlostitemapp.api

import android.content.Context
import com.example.findlostitemapp.domain.model.SingleUserPayload
import com.example.findlostitemapp.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthServices {
    @POST("auth/login")
    suspend fun login(@Body info: User.Login): Response<SingleUserPayload>

    @POST("auth/register")
    suspend fun register(@Body info: User.Register): Response<SingleUserPayload>

    companion object {
        fun getInstance(context: Context): AuthServices {
            return ApiClient.getClient(context).create(AuthServices::class.java)
        }
    }
}