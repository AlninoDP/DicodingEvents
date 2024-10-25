package com.example.dicodingevents

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dicodingevents.data.remote.retrofit.ApiConfig

class EventWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    companion object {
        private val TAG = Worker::class.java.simpleName
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "events channel"
    }


    override suspend fun doWork(): Result {
        return getActiveEvent()
    }

    private suspend fun getActiveEvent(): Result {
        try {
            val response = ApiConfig.getApiService().getEvents(limit = 1)
            val listEvent = response.listEvents
            val beginTime = listEvent[0].beginTime
            val endTime = listEvent[0].endTime
            val title = "Jangan Lewatkan Event Ini: ${listEvent[0].name!!}"
            val description = "Acara ini akan dimulai pada $beginTime dan selesai pada $endTime"
            showNotification(title, description)
            return Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching event data", e)
            showNotification("Get Event Failed", e.message)
            return Result.failure()
        }
    }

    private fun showNotification(title: String, description: String?) {
        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_active)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }
}