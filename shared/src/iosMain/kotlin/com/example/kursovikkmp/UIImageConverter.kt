package com.example.kursovikkmp

import org.jetbrains.compose.resources.DrawableResource
import platform.UIKit.UIImage
import platform.Foundation.NSBundle

fun DrawableResource.toUIImage(): UIImage? {
    return try {
        val imageName = "sample_image"
        UIImage.imageNamed(imageName) ?: UIImage.systemImageNamed("photo")
    } catch (e: Exception) {
        UIImage.systemImageNamed("photo")
    }
}