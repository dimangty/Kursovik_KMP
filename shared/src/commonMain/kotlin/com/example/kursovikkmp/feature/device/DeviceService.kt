package com.example.kursovikkmp.feature.device

expect class DeviceService {
    fun isAndroid(): Boolean
    fun isIOS(): Boolean

    fun openMailToSupport(mail: String)
    fun openUrl(urlString: String)
}