package com.example.myapplication.background

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.myapplication.DB.Nutrient
import com.example.myapplication.R

fun makeNutritionalNotification(context: Context, nutrition: Nutrient, pendingIntent: PendingIntent) {
    val channelId = "NutritionChannel"
    val channelName = "Nutrition Info Channel"
    val notificationId = 1  // 다른 유형의 알림에 대해 다른 ID 사용

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
    notificationManager.createNotificationChannel(notificationChannel)

    val message = "탄수화물: ${nutrition.carbohydrate}, 단백질: ${nutrition.protein}, 지방: ${nutrition.fat}"

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.baseline_access_alarm_24)
        .setContentTitle("식전식KU - 영양 정보")
        .setContentText(message)
        .setPriority(NotificationManager.IMPORTANCE_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(notificationId, notification)
}

/* 사용 법
val nutritionInfo = NutritionalInfo("50g", "30g", "20g")
val pendingIntent = // PendingIntent를 여기서 생성합니다.
makeNutritionalNotification(context, nutritionInfo, pendingIntent)
 */