package com.example.kursovikkmp

import org.jetbrains.compose.resources.FontResource as ComposeFontResource
import dev.icerock.moko.resources.FontResource as MokoFontResource
import platform.UIKit.UIFont


fun ComposeFontResource.toUIFont(size: Double = 16.0): UIFont {
    return try {
        UIFont.fontWithName("LatoRegular", size) ?: UIFont.systemFontOfSize(size)
    } catch (e: Exception) {
        UIFont.systemFontOfSize(size)
    }
}

fun MokoFontResource.toUIFont(size: Double = 16.0): UIFont {
    return try {
        UIFont.fontWithName("LatoRegular", size) ?: UIFont.systemFontOfSize(size)
    } catch (e: Exception) {
        UIFont.systemFontOfSize(size)
    }
}