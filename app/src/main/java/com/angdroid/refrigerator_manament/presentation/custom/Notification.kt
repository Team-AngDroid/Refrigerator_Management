package com.angdroid.refrigerator_manament.presentation.custom

import android.content.Context
import androidx.core.app.NotificationCompat
import com.angdroid.refrigerator_manament.R

class Notification(private val context: Context) {
    init {
        with(context) {
            val builder = NotificationCompat.Builder(this, getString(R.string.notification_channel))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_sub_title))
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.notification_expiration))
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }


    }
}