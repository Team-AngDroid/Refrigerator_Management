package com.angdroid.refrigerator_manament.presentation.custom

import android.os.Bundle
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityWebviewBinding
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity

class WebView : BaseActivity<ActivityWebviewBinding>(R.layout.activity_webview) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.webView.loadUrl("https://www.10000recipe.com/")
    }
}