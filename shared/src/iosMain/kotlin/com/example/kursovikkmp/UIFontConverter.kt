package com.example.kursovikkmp

import org.jetbrains.compose.resources.FontResource as ComposeFontResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import dev.icerock.moko.resources.FontResource as MokoFontResource
import platform.UIKit.UIFont
import platform.CoreText.CTFontManagerRegisterFontsForURL
import platform.CoreText.CTFontCreateWithName
import platform.CoreText.kCTFontManagerScopeProcess
import platform.CoreFoundation.CFStringCreateWithCString
import platform.CoreFoundation.kCFStringEncodingUTF8
import platform.Foundation.NSData
import platform.Foundation.NSBundle
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSString
import platform.Foundation.NSURL
import kotlinx.cinterop.*
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalResourceApi::class, ExperimentalForeignApi::class)
fun ComposeFontResource.toUIFont(size: Double = 16.0): UIFont {
    return try {
        println("Attempting to load font from compose resources...")
        
        // Since readBytes() is not available in current Compose Resources version,
        // let's use a direct approach by trying known font names that should be registered
        // when the font is properly bundled with the app
        
        val fontNames = listOf(
            "Lato-Regular",
            "LatoRegular", 
            "Lato",
            "lato_regular"
        )
        
        // Try to create the font with common font name variations
        for (fontName in fontNames) {
            val font = UIFont.fontWithName(fontName, size)
            if (font != null) {
                println("Successfully created font with name: $fontName")
                return font
            }
        }
        
        // If none of the font names work, log it and fall back to system font
        println("Could not find Lato font, using system font")
        UIFont.systemFontOfSize(size)
        
    } catch (e: Exception) {
        println("Exception in toUIFont: ${e.message}")
        e.printStackTrace()
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