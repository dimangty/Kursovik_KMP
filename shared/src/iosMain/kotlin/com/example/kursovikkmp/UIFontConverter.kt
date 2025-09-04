package com.example.kursovikkmp

import org.jetbrains.compose.resources.FontResource
import platform.UIKit.UIFont

fun FontResource.toUIFont(size: Double = 16.0): UIFont? {
    return try {
        UIFont.fontWithName("LatoRegular", size) ?: UIFont.systemFontOfSize(size)
    } catch (e: Exception) {
        UIFont.systemFontOfSize(size)
    }
}