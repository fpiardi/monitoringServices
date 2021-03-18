package com.fpiardi.monitoringservices

import android.app.Application
import com.fpiardi.monitoringservices.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // declare used Android context
            androidContext(this@BaseApplication)
            // declare modules
            modules(uiModule)
        }
    }
}