package com.example.nova

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NovaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Primary application lifecycle initialization
    }
}
