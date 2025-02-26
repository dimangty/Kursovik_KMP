package com.example.kursovikkmp.android

import android.app.Application
import com.example.kursovikkmp.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(sharedModule)
        }
    }
}