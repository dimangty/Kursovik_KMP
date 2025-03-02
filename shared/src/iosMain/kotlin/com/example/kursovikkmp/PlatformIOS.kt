package com.example.kursovikkmp

import com.example.kursovikkmp.common.view.ButtonData
import com.example.kursovikkmp.common.view.TextFontState
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.getMock
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.getUIColor
import dev.icerock.moko.resources.uiFont
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UIImage

val ImageResource.uiImage: UIImage
    get() = this.toUIImage() ?: UIImage()

val ColorResource.uiColor: UIColor
    get() = this.getUIColor()

val TextFontState.uiFont: UIFont
    get() = this.font.uiFont(withSize = this.fontSize.toDouble())

fun getTextStateMock(text: String): TextState {
    return TextState.latoRegular(14, MR.colors.black).getMock(text)
}