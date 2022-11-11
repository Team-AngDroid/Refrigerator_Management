package com.angdroid.refrigerator_manament.presentation.custom

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
import com.angdroid.refrigerator_manament.presentation.home.HomeActivity

class Notification(private val context: Context) {
    init {
        with(context) {

            //TODO HOME으로 갈지 Splash로 갈지 고민
            val resultIntent = Intent(context, HomeActivity::class.java)
            // Create the TaskStackBuilder
            val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
                // Add the intent, which inflates the back stack
                addNextIntentWithParentStack(resultIntent)
                // Get the PendingIntent containing the entire back stack
                getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
            }


            val builder = NotificationCompat.Builder(this, getString(R.string.notification_channel))
                .setSmallIcon(R.drawable.ic_refrigerator)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_sub_title))
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.notification_expiration))
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)


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