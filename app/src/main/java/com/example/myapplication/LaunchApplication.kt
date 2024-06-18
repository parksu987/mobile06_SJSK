package com.example.myapplication

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.internal.aggregatedroot.AggregatedRoot


@HiltAndroidApp
class LaunchApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        // Initialize WorkManager
        WorkManager.initialize(
            this,
            workManagerConfiguration
        )
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}