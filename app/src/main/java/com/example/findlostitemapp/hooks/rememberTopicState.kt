package com.example.findlostitemapp.hooks

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.findlostitemapp.api.TopicServices
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.exceptions.ApiExceptions
import com.example.findlostitemapp.exceptions.AuthExceptions
import com.example.findlostitemapp.ui.components.SelectOption

data class TopicState(val state: ApiState<List<Post.Topic>>, val execute: () -> Unit)

@Composable
fun rememberGetTopicState(): TopicState {
    val state = rememberApiState<List<Post.Topic>>()
    val services = TopicServices.getInstance(LocalContext.current)

    fun execute() {
        state.execute {
            try {
                val res = services.getTopics()
                if (res.isSuccessful) {
                    res.body()?.data ?: emptyList()
                } else {
                    when (res.code()) {
                        401 -> throw AuthExceptions.Unauthorized()
                        else -> throw ApiExceptions.InternalServerError()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    return remember(state.type) {
        TopicState(
            state = state,
            execute = ::execute
        )
    }
}

data class TopicSelectState(
    val state: ApiState<List<Post.Topic>>,
    var selected: SelectOption<String>,
    val options: List<SelectOption<String>>,
    val handleSelect: (SelectOption<String>) -> Unit
)

@Composable
fun rememberTopicSelectState(): TopicSelectState {
    val getTopic = rememberGetTopicState()

    val options by remember(getTopic.state.type) {
        derivedStateOf {
            val list = mutableListOf(SelectOption("", "Tất cả"))
            if (getTopic.state.data != null) {
                list.addAll(getTopic.state.data!!.map { SelectOption(it.uuid, it.name) })
            }
            list.toList()

        }
    }

    var selected by remember {
        mutableStateOf(options.first())
    }


    val handleSelect = { value: SelectOption<String> ->
        selected = value
    }

    LaunchedEffect(Unit) {
        getTopic.execute()
    }

    LaunchedEffect(getTopic.state.type) {
        if (getTopic.state.isSuccess) {
            selected = options.first()
        }
    }

    return remember(getTopic.state, selected, options) {
        TopicSelectState(
            state = getTopic.state,
            selected = selected,
            options = options,
            handleSelect = handleSelect
        )
    }
}

data class TopicDeleteState(
    val state: ApiState<Post.Topic>,
    val execute: (String) -> Unit
)

@Composable
fun rememberDeleteTopicState(): TopicDeleteState {
    val state = rememberApiState<Post.Topic>()
    val services = TopicServices.getInstance(LocalContext.current)

    fun execute(id: String) {
        state.execute {
            try {
                val res = services.deleteTopic(id)
                if (res.isSuccessful) {
                    res.body()?.data ?: throw ApiExceptions.InternalServerError()
                } else {
                    when (res.code()) {
                        401 -> throw AuthExceptions.Unauthorized()
                        404 -> throw ApiExceptions.NotFound()
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
        TopicDeleteState(
            state = state,
            execute = ::execute
        )
    }
}


data class TopicAddState(
    val state: ApiState<Post.Topic>,
    val execute: (String) -> Unit
)

@Composable
fun rememberAddTopicState(): TopicAddState {
    val state = rememberApiState<Post.Topic>()
    val services = TopicServices.getInstance(LocalContext.current)

    fun execute(topicName: String) {
        state.execute {
            try {
                val res = services.addTopic(Post.TopicRequest(topicName))
                if (res.isSuccessful) {
                    res.body()?.data ?: throw ApiExceptions.InternalServerError()
                } else {
                    when (res.code()) {
                        401 -> throw AuthExceptions.Unauthorized()
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
        TopicAddState(
            state = state,
            execute = ::execute
        )
    }
}
