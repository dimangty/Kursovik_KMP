package com.example.kursovikkmp

import androidx.compose.ui.graphics.Color
import platform.UIKit.UIColor

fun Color.toUIColor(): UIColor {
    return UIColor.colorWithRed(
        red = this.red.toDouble(),
        green = this.green.toDouble(), 
        blue = this.blue.toDouble(),
        alpha = this.alpha.toDouble()
    )
}

object AppColorsUIKit {
    val Primary: UIColor = AppColors.Primary.toUIColor()
}