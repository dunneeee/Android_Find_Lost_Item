package com.example.findlostitemapp.domain.model

import android.net.Uri
import java.io.File

object Post {
    object Status {
        const val PUBLISHED = "published"
        const val PENDING = "pending"
        const val REJECTED = "rejected"
    }

    data class Image(
        val uuid: String,
        val url: String,
        val createdAt: String,
        val updatedAt: String
    )

    data class Topic(
        val uuid: String,
        val name: String,
        val createdAt: String,
        val updatedAt: String
    )

    data class Instance(
        val uuid: String,
        val title: String,
        val content: String,
        val location: String,
        val status: String,
        val user: User.Instance,
        val topic: Topic,
        val images: List<Image>
    )

    data class Upload(
        val topic: String,
        val title: String,
        val content: String,
        val location: String,
        val images: List<Uri>
    )

    data class CountPending(val count: Int)
    data class TopicRequest(val name: String)
}

typealias PostPayload = Http.Payload<List<Post.Instance>>

typealias SinglePostPayload = Http.Payload<Post.Instance>

typealias TopicPayload = Http.Payload<List<Post.Topic>>

typealias SingleTopicPayload = Http.Payload<Post.Topic>

typealias CountPendingPostPayload = Http.Payload<Post.CountPending>
