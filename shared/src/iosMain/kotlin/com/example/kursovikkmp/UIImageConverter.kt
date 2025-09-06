package com.example.kursovikkmp

import org.jetbrains.compose.resources.DrawableResource
import platform.UIKit.UIImage
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSData
import platform.Foundation.NSBundle
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.CoreGraphics.CGContextRef
import platform.CoreGraphics.CGPathRef
import platform.CoreGraphics.CGMutablePathRef
import platform.CoreGraphics.CGPathCreateMutable
import platform.CoreGraphics.CGPathMoveToPoint
import platform.CoreGraphics.CGPathAddLineToPoint
import platform.CoreGraphics.CGPathAddCurveToPoint
import platform.CoreGraphics.CGContextAddPath
import platform.CoreGraphics.CGContextSetFillColorWithColor
import platform.CoreGraphics.CGContextFillPath
import platform.UIKit.UIColor



@OptIn(ExperimentalForeignApi::class, ExperimentalResourceApi::class)
fun DrawableResource.toUIImage(): UIImage? {
    return try {
        println("Attempting to convert DrawableResource to UIImage...")
        
        // Use proper Compose Resources API to read the drawable content
        val xmlBytes = runBlocking {
            org.jetbrains.compose.resources.getDrawableResourceBytes(
                org.jetbrains.compose.resources.getSystemResourceEnvironment(),
                this@toUIImage
            )
        }
        val xmlContent = xmlBytes.decodeToString()
        
        println("Loaded XML content: ${xmlContent.substring(0, minOf(100, xmlContent.length))}...")
        
        // Parse dimensions from the XML vector drawable
        val widthMatch = Regex("""android:width="(\d+)dp"""").find(xmlContent)
        val heightMatch = Regex("""android:height="(\d+)dp"""").find(xmlContent)
        
        val drawableWidth = widthMatch?.groupValues?.get(1)?.toDoubleOrNull() ?: 360.0
        val drawableHeight = heightMatch?.groupValues?.get(1)?.toDoubleOrNull() ?: 40.0
        
        println("Parsed dimensions from XML: ${drawableWidth}x${drawableHeight}")
        
        // Create a UIImage with the correct dimensions using Core Graphics
        val size = platform.CoreGraphics.CGSizeMake(drawableWidth, drawableHeight)
        
        platform.UIKit.UIGraphicsBeginImageContextWithOptions(size, false, 0.0)
        val context = platform.UIKit.UIGraphicsGetCurrentContext()
        
        if (context != null) {
            // Parse and render the vector paths
            renderVectorPaths(context, xmlContent, drawableWidth, drawableHeight)
        }
        
        val image = platform.UIKit.UIGraphicsGetImageFromCurrentImageContext()
        platform.UIKit.UIGraphicsEndImageContext()
        
        if (image != null) {
            println("Successfully created UIImage with size: ${drawableWidth}x${drawableHeight}")
        } else {
            println("Failed to create UIImage from vector data")
        }
        
        image
    } catch (e: Exception) {
        println("Exception in toUIImage: ${e.message}")
        e.printStackTrace()
        null
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun renderVectorPaths(context: CGContextRef, xmlContent: String, width: Double, height: Double) {
    try {
        // Extract all path elements from the XML
        val pathRegex = Regex("""<path[^>]*android:pathData="([^"]*)"[^>]*android:fillColor="([^"]*)"[^>]*>""")
        val paths = pathRegex.findAll(xmlContent)
        
        for (pathMatch in paths) {
            val pathData = pathMatch.groupValues[1]
            val fillColor = pathMatch.groupValues[2]
            
            println("Processing path with color $fillColor: ${pathData.substring(0, minOf(50, pathData.length))}...")
            
            // Create CGPath from the path data
            val cgPath = createCGPathFromAndroidPath(pathData)
            
            if (cgPath != null) {
                // Set fill color
                val color = parseColor(fillColor)
                if (color != null) {
                    platform.CoreGraphics.CGContextSetFillColorWithColor(context, color.CGColor)
                }
                
                // Add path to context and fill
                platform.CoreGraphics.CGContextAddPath(context, cgPath)
                platform.CoreGraphics.CGContextFillPath(context)
            }
        }
    } catch (e: Exception) {
        println("Error rendering vector paths: ${e.message}")
        e.printStackTrace()
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun createCGPathFromAndroidPath(pathData: String): CGMutablePathRef? {
    try {
        val path = platform.CoreGraphics.CGPathCreateMutable()
        if (path == null) return null
        
        // Simple path parsing - this is a basic implementation
        // For production, you'd need a more robust SVG path parser
        val commands = pathData.split("(?=[MLHVCSQTAZ])".toRegex()).filter { it.isNotEmpty() }
        
        var currentX = 0.0
        var currentY = 0.0
        
        for (command in commands) {
            if (command.isEmpty()) continue
            
            val commandType = command[0]
            val params = command.substring(1).trim().split("[ ,]+".toRegex()).mapNotNull { it.toDoubleOrNull() }
            
            when (commandType) {
                'M' -> {
                    if (params.size >= 2) {
                        currentX = params[0]
                        currentY = params[1]
                        platform.CoreGraphics.CGPathMoveToPoint(path, null, currentX, currentY)
                    }
                }
                'L' -> {
                    if (params.size >= 2) {
                        currentX = params[0]
                        currentY = params[1]
                        platform.CoreGraphics.CGPathAddLineToPoint(path, null, currentX, currentY)
                    }
                }
                'H' -> {
                    if (params.size >= 1) {
                        currentX = params[0]
                        platform.CoreGraphics.CGPathAddLineToPoint(path, null, currentX, currentY)
                    }
                }
                'V' -> {
                    if (params.size >= 1) {
                        currentY = params[0]
                        platform.CoreGraphics.CGPathAddLineToPoint(path, null, currentX, currentY)
                    }
                }
                'Z' -> {
                    platform.CoreGraphics.CGPathCloseSubpath(path)
                }
            }
        }
        
        return path
    } catch (e: Exception) {
        println("Error creating CGPath: ${e.message}")
        return null
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun parseColor(colorString: String): UIColor? {
    return try {
        when {
            colorString.startsWith("#") -> {
                // Parse hex color
                val hex = colorString.substring(1)
                when (hex.length) {
                    6 -> {
                        val r = hex.substring(0, 2).toInt(16) / 255.0
                        val g = hex.substring(2, 4).toInt(16) / 255.0
                        val b = hex.substring(4, 6).toInt(16) / 255.0
                        UIColor.colorWithRed(r, g, b, 1.0)
                    }
                    8 -> {
                        val a = hex.substring(0, 2).toInt(16) / 255.0
                        val r = hex.substring(2, 4).toInt(16) / 255.0
                        val g = hex.substring(4, 6).toInt(16) / 255.0
                        val b = hex.substring(6, 8).toInt(16) / 255.0
                        UIColor.colorWithRed(r, g, b, a)
                    }
                    else -> UIColor.blackColor
                }
            }
            else -> UIColor.blackColor
        }
    } catch (e: Exception) {
        println("Error parsing color $colorString: ${e.message}")
        UIColor.blackColor
    }
}

val DrawableResource.uiImage: UIImage
    get() {
        val result = this.toUIImage()
        if (result == null) {
            println("toUIImage returned null, creating emergency fallback")
            return UIImage()
        }
        return result
    }