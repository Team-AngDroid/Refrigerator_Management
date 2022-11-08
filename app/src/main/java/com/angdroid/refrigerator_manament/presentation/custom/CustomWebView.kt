package com.angdroid.refrigerator_manament.presentation.custom

import android.os.Bundle
import android.webkit.WebViewClient
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityWebviewBinding
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomWebView : BaseActivity<ActivityWebviewBinding>(R.layout.activity_webview) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getResult()
    }

    private fun getResult() {
        val link = intent.getStringExtra("link")
        binding.webView.webViewClient = CustomWebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        // 만개의 레시피 웹페이자 내부가 javaScript를 통한
        //동작 동적이 있음 따라서 ture로 설정
        binding.webView.loadUrl(link.toString())

        binding.webviewAppbar.setNavigationOnClickListener {
            finish()
        }
    }

}

