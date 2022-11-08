package com.angdroid.refrigerator_manament.presentation.custom

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity
import timber.log.Timber

class CustomWebViewClient(val context: Context) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val requestUrl = request!!.url.toString()
        Timber.e(requestUrl.toString())
        return if ( requestUrl.startsWith("https://") || requestUrl.startsWith("http://")) {
            super.shouldOverrideUrlLoading(view, request)
        } else {
            startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(requestUrl)), null)
            // 만개의 레시피 내부에서 일부 링크는 https 형태가 아닌 링크를 누르면 본인들 앱으로 연결되는 것들 (ex 쿠팡)이 있음
            // 따라서 이런 애들은 브루우저처럼 자체앱 혹은 구글플레이로 연결되도록 암시적 인텐트로 별개 창을 열어줌
            true
        }

    }
}