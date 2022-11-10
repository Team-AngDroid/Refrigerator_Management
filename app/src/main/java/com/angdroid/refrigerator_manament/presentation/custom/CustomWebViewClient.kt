package com.angdroid.refrigerator_manament.presentation.custom

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity

class CustomWebViewClient(val context: Context) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val requestUrl = request!!.url.toString()
        if (requestUrl.startsWith("https://") || requestUrl.startsWith("http://")) {
            return super.shouldOverrideUrlLoading(view, request)
        } else if (requestUrl.startsWith("market://")) {
            startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(requestUrl)), null)
            // 만개의 레시피 내부에서 일부 링크는 https 형태가 아닌 링크를 누르면 본인들 앱 설치하라고 마켓으로 연결되는 것들이 있음
            // 따라서 이런 애들은 브라우저처럼 자체앱 혹은 구글플레이로 연결되도록 암시적 인텐트로 별개 창을 열어줌
            return true
        } else { 
            // 다이나믹 링크의 경우 이런식으로 아무동작 안하게끔 처리 해줘서 알아서 https:// 형태로 로드 되도록
        }
        return true
    }
}
