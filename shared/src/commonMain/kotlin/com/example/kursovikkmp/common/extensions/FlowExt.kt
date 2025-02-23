package com.example.kursovikkmp.shared.common.extension

import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

// For iOS
fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)

// For iOS
class CommonFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {
    private val scope = MainScope()

    private var observer: Closeable? = null

    fun watchSingle(block: (T) -> Unit): Closeable {
        observer?.close()
        observer = null
        val job = onEach(block).launchIn(scope)
        val subscription = object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
        observer = subscription
        return subscription
    }

    fun watch(block: (T) -> Unit): Closeable {
        val job = onEach(block).launchIn(scope)
        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}
