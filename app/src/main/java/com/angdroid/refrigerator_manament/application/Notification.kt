package com.angdroid.refrigerator_manament.application

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.presentation.SplashActivity

class Notification(
    private val context: Context,
    private val title: String,
    private val body: String
) {
    init {
        with(context) {
            val builder = setBuilder()
            createNotificationChannel()

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, builder.build())
        }
    }

    private fun setBuilder(): NotificationCompat.Builder {
        val resultPendingIntent = setPendingIntent()

        return with(context) {
            NotificationCompat.Builder(this, getString(R.string.notification_channel))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(body) // 줄넘김을 위해서 확장 알림으로 설정
                )
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setFullScreenIntent(resultPendingIntent , true)
        }
    }

    private fun setPendingIntent(): PendingIntent {
        //TODO HOME으로 갈지 Splash로 갈지 고민 Splash가 가장 깔끔하다고 생각
        val resultIntent = Intent(context, SplashActivity::class.java)

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel)
            val descriptionText = "Description"
            val importance = NotificationManager.IMPORTANCE_MAX
            val channel = NotificationChannel(
                context.getString(R.string.notification_channel),
                name,
                importance
            ).apply {
                description = descriptionText
                setShowBadge(true)
                enableVibration(true)
                enableLights(true)
                lockscreenVisibility = android.app.Notification.VISIBILITY_PUBLIC
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

}