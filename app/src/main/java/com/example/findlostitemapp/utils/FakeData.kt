package com.example.findlostitemapp.utils

import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.domain.model.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object FakeData {
    private val users = mutableListOf<User.Instance>()
    private val posts = mutableListOf<Post.Instance>()
    private val topic = mutableListOf<Post.Topic>()
    fun getRandUser(): User.Instance {
        return users.random()
    }

    fun getRandPost(): Post.Instance = posts.random()

    fun getPosts(): List<Post.Instance> = posts

    fun getTopics(): List<Post.Topic> = topic

    init {
        topic.add(
            Post.Topic(
                "1",
                "Technology",
                "2021-10-01 12:00:00",
                "2021-10-01 12:00:00"
            )
        )
        topic.add(
            Post.Topic(
                "2",
                "Travel",
                "2021-10-02 12:00:00",
                "2021-10-02 12:00:00"
            )
        )

        topic.add(
            Post.Topic(
                "3",
                "Food",
                "2021-10-03 12:00:00",
                "2021-10-03 12:00:00"
            )
        )
    }
}