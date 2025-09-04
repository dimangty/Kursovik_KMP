package com.example.kursovikkmp

import org.jetbrains.compose.resources.DrawableResource
import platform.UIKit.UIImage
import platform.Foundation.NSBundle

fun DrawableResource.toUIImage(): UIImage? {
    return try {
        val bundle = NSBundle.mainBundle
        val resourceName = this.toString().substringAfterLast("/").substringBeforeLast(".")
        
        // Try loading from compose resources bundle
        val composeResourcesPath = bundle.pathForResource("compose-resources", ofType = null)
        composeResourcesPath?.let { basePath ->
            val imagePath = "$basePath/drawable/$resourceName.jpg"
            UIImage.imageWithContentsOfFile(imagePath)?.let { return it }
        }
        
        // Try loading from main bundle directly
        val directPath = bundle.pathForResource(resourceName, ofType = "jpg")
        directPath?.let { UIImage.imageWithContentsOfFile(it) }
            ?: bundle.pathForResource(resourceName, ofType = "png")?.let { UIImage.imageWithContentsOfFile(it) }
            ?: UIImage.imageNamed(resourceName)
    } catch (e: Exception) {
        null
    }
}

val DrawableResource.uiImage: UIImage
    get() = this.toUIImage() ?: UIImage()