package com.example.findlostitemapp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

object CoroutineUtils {
    val ioScope = CoroutineScope(Dispatchers.IO)
    val mainScope = CoroutineScope(Dispatchers.Main)
    val defaultScope = CoroutineScope(Dispatchers.Default)
    val unconfinedScope = CoroutineScope(Dispatchers.Unconfined)

    fun io(block: suspend CoroutineScope.() -> Unit) {
        ioScope.launch(block = block)
    }

    fun main(block: suspend CoroutineScope.() -> Unit) {
        mainScope.launch(block = block)
    }

    fun default(block: suspend CoroutineScope.() -> Unit) {
        defaultScope.launch(block = block)
    }

    fun unconfined(block: suspend CoroutineScope.() -> Unit) {
        unconfinedScope.launch(block = block)
    }

    fun cancelAll() {
        ioScope.coroutineContext.cancelChildren()
        mainScope.coroutineContext.cancelChildren()
        defaultScope.coroutineContext.cancelChildren()
        unconfinedScope.coroutineContext.cancelChildren()
    }
}