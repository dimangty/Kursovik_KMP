package com.example.kursovikkmp

import org.jetbrains.compose.resources.DrawableResource
import platform.UIKit.UIImage
import platform.Foundation.NSBundle

fun DrawableResource.toUIImage(): UIImage? {
    return try {
        return UIImage()
    } catch (e: Exception) {
        null
    }
}

val DrawableResource.uiImage: UIImage
    get() = this.toUIImage() ?: UIImage()