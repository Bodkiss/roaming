package com.knowroaming.esim.app

import android.app.Application
import com.knowroaming.esim.app.util.injection.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppMain : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppMain)
            androidLogger()
            modules(appModule())
        }
    }
}