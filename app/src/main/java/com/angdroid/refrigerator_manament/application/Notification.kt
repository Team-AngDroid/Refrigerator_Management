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
import com.angdroid.refrigerator_manament.presentation.home.HomeActivity

class Notification(private val context: Context) {
    init {
        with(context) {
            val builder = setBuilder()
            createNotificationChannel()

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, builder.build())
        }
    }

    private fun setBuilder(): NotificationCompat.Builder{
        val resultPendingIntent = setPendingIntent()

        return with(context){
            NotificationCompat.Builder(this, getString(R.string.notification_channel))
                .setSmallIcon(R.drawable.group_1221)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_sub_title))
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.notification_expiration))
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
        }
    }

    private fun setPendingIntent():PendingIntent{
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