package com.angdroid.refrigerator_manament.presentation.custom

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.angdroid.refrigerator_manament.R

class Notification(private val context: Context) {
    init {
        with(context) {
            val builder = NotificationCompat.Builder(this, getString(R.string.notification_channel))
                .setSmallIcon(R.drawable.ic_refrigerator)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_sub_title))
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.notification_expiration))
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            createNotificationChannel()
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel)
            val descriptionText = "Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                context.getString(R.string.notification_channel),
                name,
                importance
            ).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

}