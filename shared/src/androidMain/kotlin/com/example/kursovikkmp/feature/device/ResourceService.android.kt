package com.example.kursovikkmp.feature.device

import android.content.Context
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc

actual class ResourceService(private val context: Context) {

    actual fun getString(stringRes: StringResource): String {
        return StringDesc.Resource(stringRes).toString(context)
    }

    actual fun getString(
        stringRes: StringResource,
        args: List<String>
    ): String {
        return StringDesc.ResourceFormatted(stringRes, args).toString(context)
    }
}