package com.angdroid.refrigerator_manament.application

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Notification(applicationContext)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}