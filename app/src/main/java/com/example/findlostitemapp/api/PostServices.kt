package com.example.findlostitemapp.api

import android.content.Context
import com.example.findlostitemapp.domain.model.Http
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.domain.model.PostPayload
import com.example.findlostitemapp.domain.model.SearchData
import com.example.findlostitemapp.domain.model.SinglePostPayload
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface PostServices {
    @GET("posts/recommended")
    suspend fun getRecommendedPosts(@Query("page") page: Int = 1, @Query("size") size: Int = 10): Response<PostPayload>

    @GET("posts/count")
    suspend fun getPostCount(): Response<Http.Payload<Int>>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: String): Response<SinglePostPayload>

    @POST("me/posts/add")
    @Multipart
    suspend fun addPost(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>, @Part images:
        List<MultipartBody.Part>
    ):
            Response<SinglePostPayload>

    @GET("posts/search")
    suspend fun searchPosts(@QueryMap query: Map<String, String>): Response<PostPayload>

    @PUT("admin/posts/{uuid}/accept")
    suspend fun acceptPost(@Path("uuid") uuid: String): Response<SinglePostPayload>

    @PUT("admin/posts/{uuid}/reject")
    suspend fun rejectPost(@Path("uuid") uuid: String): Response<SinglePostPayload>

    @GET("admin/posts/pending")
    suspend fun getPendingPosts(@Query("page") page: Int = 1, @Query("size") size: Int = 10): Response<PostPayload>

    companion object {
        fun getInstance(context: Context): PostServices = ApiClient.getClient(context).create(PostServices::class.java)
    }
}