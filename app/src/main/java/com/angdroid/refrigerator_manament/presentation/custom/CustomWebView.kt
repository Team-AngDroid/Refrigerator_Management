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
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(link.toString())
    }

}