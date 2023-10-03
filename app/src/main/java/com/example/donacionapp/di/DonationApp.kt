package com.example.donacionapp.di

import android.app.Application
import com.example.donacionapp.di.module.appModule
import com.example.donacionapp.di.module.donorsModule
import org.koin.core.context.startKoin

class DonationApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule, donorsModule)
        }
    }
}