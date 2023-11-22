package com.example.kmptest.android

import android.app.Application
import com.example.kmptest.di.appModule
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule())
        }
    }
}