package com.angdroid.refrigerator_manament.application

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        //super.onMessageReceived(message)
        if(message.notification != null){
            Notification( // 파이어베이스 기본 noti써도 되지만
                // 확장형 노티등등 세부적으로 설정을 위해 Notification 클래스 호출
                applicationContext,
                message.notification!!.title.toString(), // 콘솔 기준 알림 제목
                message.notification!!.body.toString() // 콘솔 기준 알림 텍스트
            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}