package com.example.kursovikkmp.feature.device
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc

class ResourceServiceImpl : ResourceService {
    override fun getString(stringRes: StringResource): String {
        return StringDesc.Resource(stringRes).localized()
    }

    override fun getString(
        stringRes: StringResource,
        args: List<String>
    ): String {
        return StringDesc.ResourceFormatted(stringRes, args).localized()
    }
}
