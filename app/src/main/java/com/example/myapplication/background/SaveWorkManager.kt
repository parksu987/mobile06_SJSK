package com.example.myapplication.background

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.example.myapplication.background.SaveWorker
import com.example.myapplication.viewmodel.EatingViewModel
import com.example.myapplication.viewmodel.LoginViewModel
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.concurrent.TimeUnit

class SaveWorkManager(context:Context, val loginViewModel: LoginViewModel, val eatingViewModel: EatingViewModel) {
    var context: Context = context

    val calendar = Calendar.getInstance()
    val delay = calculateDelay(calendar)

    var days = loginViewModel.person.value?.intake?.keys
    val yesterday = LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.DAYS).toString()

    fun makeNotifyWorkRequest() {
        if(days == null) return

        if(days!!.contains<String>(yesterday)){
            val myData: Data = workDataOf(
                "carbohydrate" to loginViewModel.person.value?.intake?.get(yesterday)?.carbohydrate,
                "protein" to loginViewModel.person.value?.intake?.get(yesterday)?.protein,
                "fat" to loginViewModel.person.value?.intake?.get(yesterday)?.fat
            )

            val workRequest:WorkRequest = PeriodicWorkRequestBuilder<SaveWorker>(24, TimeUnit.HOURS)
                .setInputData(myData)
                .setInitialDelay(delay, TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }

//    fun enqueueWorkRequest() {
//        //WorkManager.getInstance(context).enqueue(workRequest)
//    }

    private fun calculateDelay(startCalendar: Calendar): Long {
        val todayCalendar = Calendar.getInstance()
        todayCalendar.set(Calendar.MINUTE, 0)
        todayCalendar.set(Calendar.HOUR, 8)
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