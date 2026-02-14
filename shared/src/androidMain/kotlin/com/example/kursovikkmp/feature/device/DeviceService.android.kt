package com.example.kursovikkmp.feature.device

import android.app.Application
import android.content.Intent
import android.net.Uri

class DeviceServiceImpl(private val appContext: Application) : DeviceService {
    override fun isAndroid(): Boolean {
        return true
    }

    override fun isIOS(): Boolean {
        return false
    }

    override fun openMailToSupport(mail: String) {
    }

    override fun openUrl(urlString: String) {
        var browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(urlString)
        )

        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        appContext.startActivity(browserIntent)
    }

}
