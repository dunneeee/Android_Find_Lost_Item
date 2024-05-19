package com.example.findlostitemapp.api

import android.content.Context
import com.example.findlostitemapp.domain.model.Http
import retrofit2.Response
import retrofit2.http.GET

interface UserServices {
    @GET("public-users/count")
    suspend fun getUserCount(): Response<Http.Payload<Int>>


    companion object {
        fun getInstance(context: Context): UserServices = ApiClient.getClient(context).create(UserServices::class.java)
    }
}