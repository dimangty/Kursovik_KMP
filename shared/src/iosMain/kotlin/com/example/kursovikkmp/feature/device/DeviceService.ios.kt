package com.example.kursovikkmp.feature.device

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class DeviceService {
    actual fun isAndroid(): Boolean {
        return false
    }

    actual fun isIOS(): Boolean {
        return true
    }

    actual fun openMailToSupport(mail: String) {

    }

    actual fun openUrl(urlString: String) {
        val url = NSURL.URLWithString(urlString)
        if (url != null) {
            val application = UIApplication.sharedApplication
            if (application.canOpenURL(url)) {
                application.openURL(url, mapOf<Any?, Any?>(), null)
            }
        }
    }

}