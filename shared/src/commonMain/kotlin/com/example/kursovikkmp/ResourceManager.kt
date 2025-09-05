package com.example.kursovikkmp

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import kursovikkmp.shared.generated.resources.*

class ResourceManager {
    companion object {
        fun getColor(): Color {
            return AppColors.Primary
        }

        fun getAppName(): String {
            return "TestCmp App"
        }

        fun getFontName(): String {
            return "lato_regular"
        }

        fun getFontResource(): FontResource {
            return Res.font.lato_regular
        }

        fun getDrawableResource(): DrawableResource {
            return Res.drawable.ic_example
        }

        fun getStringResource(): StringResource {
            return Res.string.app_name
        }
    }
}