package com.example.findlostitemapp.api

import android.content.Context
import com.example.findlostitemapp.ui.auth.AuthLocalStore
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token = AuthLocalStore(context).encodedToken()
        if (token != null) {
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Basic $token")
                .build()
            request = newRequest
        }
        return chain.proceed(request)
    }
}

object ApiClient {
    fun getClient(context: Context): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
        return retrofit
    }
}