package com.example.kursovikkmp.feature.device

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri

actual class DeviceService(private val appContext: Application) {
    actual fun isAndroid(): Boolean {
        return true
    }

    actual fun isIOS(): Boolean {
        return false
    }

    actual fun openMailToSupport(mail: String) {
    }

    actual fun openUrl(urlString: String) {
        var browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(urlString)
        )

        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        appContext.startActivity(browserIntent)
    }

}