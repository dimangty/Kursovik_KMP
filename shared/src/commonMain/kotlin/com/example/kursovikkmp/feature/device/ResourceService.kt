package com.example.kursovikkmp.feature.device
import dev.icerock.moko.resources.StringResource

expect class ResourceService {
    fun getString(stringRes: StringResource): String
    fun getString(stringRes: StringResource, args: List<String>): String
}