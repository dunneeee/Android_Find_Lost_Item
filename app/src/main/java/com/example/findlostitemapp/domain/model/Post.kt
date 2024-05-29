package com.example.findlostitemapp.domain.model

import android.content.Context
import android.net.Uri
import com.example.findlostitemapp.utils.FileUtils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
    ) {
        fun toMap(): Map<String, RequestBody> {
            val map = mutableMapOf<String, RequestBody>()
            map["topic"] = topic.toRequestBody()
            map["title"] = title.toRequestBody()
            map["content"] = content.toRequestBody()
            map["location"] = location.toRequestBody()
            return map
        }

        fun getImages(context: Context): List<MultipartBody.Part> {
            return images.map {
                val file = FileUtils.uriToFile(context, it, it.lastPathSegment)
                    ?: File("")
                MultipartBody.Part.createFormData(
                    "images[]",
                    file.name,
                    file.asRequestBody("image/*".toMediaType())
                )
            }
        }
    }

    data class CountPending(val count: Int)
    data class TopicRequest(val name: String)
}

typealias PostPayload = Http.Payload<List<Post.Instance>>

typealias SinglePostPayload = Http.Payload<Post.Instance>

typealias TopicPayload = Http.Payload<List<Post.Topic>>

typealias SingleTopicPayload = Http.Payload<Post.Topic>

typealias CountPendingPostPayload = Http.Payload<Post.CountPending>
