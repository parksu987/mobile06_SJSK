package com.example.myapplication.background

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.myapplication.background.SaveWorker
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.concurrent.TimeUnit

class SaveWorkManager {
    lateinit var context: Context

    val calendar = Calendar.getInstance()
    val delay = calculateDelay(calendar)

    val workRequest:WorkRequest = PeriodicWorkRequestBuilder<SaveWorker>(24, TimeUnit.HOURS)
        .setInitialDelay(delay, TimeUnit.SECONDS)
        .build()

    fun enqueueWorkRequest() {
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    private fun calculateDelay(startCalendar: Calendar): Long {
        val todayCalendar = Calendar.getInstance()
        todayCalendar.set(Calendar.MINUTE, 0)
        todayCalendar.set(Calendar.HOUR, 4)
        todayCalendar.set(Calendar.AM_PM, Calendar.AM)
        todayCalendar.add(Calendar.DAY_OF_YEAR, 0)

        var delay = (todayCalendar.time.time - startCalendar.time.time) / 1000
        Log.d("delay", delay.toString())
        return if (delay < 0) {
            todayCalendar.add(Calendar.DAY_OF_YEAR, 1)
            (todayCalendar.time.time - startCalendar.time.time) / 1000
        } else {
            delay
        }
    }
}