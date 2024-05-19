package com.example.findlostitemapp.api

import android.content.Context
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.domain.model.SingleTopicPayload
import com.example.findlostitemapp.domain.model.TopicPayload
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TopicServices {

    @GET("topic")
    suspend fun getTopics(): Response<TopicPayload>

    @POST("topic/add")
    suspend fun addTopic(@Body topic: Post.TopicRequest): Response<SingleTopicPayload>

    companion object {
        fun getInstance(context: Context): TopicServices =
            ApiClient.getClient(context).create(TopicServices::class.java)
    }
}