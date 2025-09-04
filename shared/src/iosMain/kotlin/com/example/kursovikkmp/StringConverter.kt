package com.example.kursovikkmp

import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import kotlinx.coroutines.runBlocking

fun StringResource.toNativeString(): String {
    return try {
        runBlocking {
            getString(this@toNativeString)
        }
    } catch (e: Exception) {
        ""
    }
}

object AppStringsNative {
    val AppName: String
        get() = ResourceManager.getStringResource().toNativeString()
}