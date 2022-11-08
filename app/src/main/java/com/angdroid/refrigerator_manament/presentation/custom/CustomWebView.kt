package com.angdroid.refrigerator_manament.presentation.custom

import android.os.Bundle
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityWebviewBinding
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomWebView : BaseActivity<ActivityWebviewBinding>(R.layout.activity_webview) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getResult()
        //binding.webView.loadUrl("https://www.10000recipe.com/")
    }

    private fun getResult() {
        val link = intent.getStringExtra("link")
        binding.webView.loadUrl(link.toString())
    }

}