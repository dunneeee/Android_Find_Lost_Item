package com.example.findlostitemapp.ui.topicManager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.LocalSnackBar
import com.example.findlostitemapp.domain.model.NotificationArgs
import com.example.findlostitemapp.domain.model.Post
import com.example.findlostitemapp.hooks.rememberAddTopicState
import com.example.findlostitemapp.hooks.rememberDeleteTopicState
import com.example.findlostitemapp.hooks.rememberGetTopicState
import com.example.findlostitemapp.providers.LocalNotification
import com.example.findlostitemapp.ui.MainLayout
import com.example.findlostitemapp.ui.components.CustomButton
import com.example.findlostitemapp.ui.components.DataNotFound
import kotlinx.coroutines.launch


@Composable
fun TopicManagerScreen(modifier: Modifier = Modifier) {
    MainLayout(topBar = { TopicManagerTopBar() }, modifier = modifier) {
        TopicManagerContent(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun TopicManagerContent(modifier: Modifier = Modifier) {
    val getTopicState = rememberGetTopicState()
    val deleteTopicState = rememberDeleteTopicState()
    val addTopicState = rememberAddTopicState()
    val snackBarState = LocalSnackBar.current
    val notifyState = LocalNotification.current

    var editTopic by remember {
        mutableStateOf<Post.Topic?>(null)
    }

    var openDialog by remember {
        mutableStateOf(false)
    }
    val handleOpenEditDialog = { topic: Post.Topic ->
        openDialog = true
    }

    val handleEditTopic = { topic: Post.Topic ->
        // TODO: handle edit topic
    }

    val handleAddTopic = { topic: Post.Topic ->
        addTopicState.execute(topic.name)
    }

    val handleOpenAddDialog = {
        openDialog = true
    }

    val handleDeleteClick = { topic: Post.Topic ->
        notifyState.showNotification(
            NotificationArgs(
                title = "Xóa chủ đề",
                content = {
                    Text(text = "Bạn có chắc chắn muốn xóa chủ đề này không?")
                },
                onConfirm = {
                    deleteTopicState.execute(topic.uuid)
                },
            )
        )
    }

    LaunchedEffect(Unit) {
        getTopicState.execute()
    }

    LaunchedEffect(getTopicState.state.type) {
        if (getTopicState.state.isError) {
            snackBarState.showSnackbar(
                message = "Error: ${getTopicState.state.error?.message!!}",
                duration = SnackbarDuration.Long
            )
        }
    }

    LaunchedEffect(deleteTopicState.state.type) {
        if (deleteTopicState.state.isError) {
            println(deleteTopicState.state.error)
            snackBarState.showSnackbar(
                message = "Error: ${getTopicState.state.error?.message!!}",
                duration = SnackbarDuration.Long
            )
        }

        if (deleteTopicState.state.isSuccess) {
            launch {
                getTopicState.state.data?.run {
                    getTopicState.state.data = this.filter { it.uuid != deleteTopicState.state.data?.uuid }
                }
            }
            snackBarState.showSnackbar(
                message = "Xóa chủ đề thành công",
                duration = SnackbarDuration.Long
            )

        }


    }

    LaunchedEffect(addTopicState.state.type) {
        if (addTopicState.state.isError) {
            snackBarState.showSnackbar(
                message = "Error: ${addTopicState.state.error?.message!!}",
                duration = SnackbarDuration.Long
            )
        }

        if (addTopicState.state.isSuccess) {
            launch {
                val topic = addTopicState.state.data!!
                getTopicState.state.data?.let {
                    getTopicState.state.data = it + topic
                }
            }
            snackBarState.showSnackbar(
                message = "Thêm chủ đề thành công",
                duration = SnackbarDuration.Long
            )
        }
    }

    TopicManagerDialog(open = openDialog, onDismiss = {
        openDialog = false
    }, topic = editTopic, onConfirm = {
        if (editTopic == null) handleAddTopic(it) else handleEditTopic(it)
    })

    Column(modifier = modifier.padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Chủ đề", style = MaterialTheme.typography.headlineSmall)
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = handleOpenAddDialog, shape = RoundedCornerShape(8.dp)) {
                Text(text = "Thêm chủ đề")
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        if (getTopicState.state.isLoading) {
            CircularProgressIndicator()
        } else {
            if(getTopicState.state.data != null && getTopicState.state.data?.isNotEmpty()!!) {
                getTopicState.state.data?.forEachIndexed { _, topic ->
                    TopicManagerItem(
                        topic = topic, modifier = Modifier.padding(vertical = 8.dp), onDeleteClick =
                        { handleDeleteClick(topic) }, onCardClick = {
                            handleOpenEditDialog(topic)
                        }
                    )

                }
            }else {
                DataNotFound()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicManagerTopBar(modifier: Modifier = Modifier) {
    TopAppBar(title = {
        Text(text = "Topic Manager")
    }, modifier = modifier)
}

