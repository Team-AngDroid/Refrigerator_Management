package com.angdroid.refrigerator_manament.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.presentation.custom.Notification
import com.angdroid.refrigerator_manament.presentation.home.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.notify

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        //TODO 호출 시점은 좀 고민해봐야 할듯
        Notification(this)

        val intent = Intent(this, HomeActivity::class.java)
        lifecycleScope.launch {
            delay(1500)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }/**/
    }
}