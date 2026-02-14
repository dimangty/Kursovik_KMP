package com.example.kursovikkmp.feature.device

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class DeviceServiceImpl : DeviceService {
    override fun isAndroid(): Boolean {
        return false
    }

    override fun isIOS(): Boolean {
        return true
    }

    override fun openMailToSupport(mail: String) {

    }

    override fun openUrl(urlString: String) {
        val url = NSURL.URLWithString(urlString)
        if (url != null) {
            val application = UIApplication.sharedApplication
            if (application.canOpenURL(url)) {
                application.openURL(url, mapOf<Any?, Any?>(), null)
            }
        }
    }

}
