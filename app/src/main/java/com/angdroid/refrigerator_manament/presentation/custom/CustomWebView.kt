package com.angdroid.refrigerator_manament.presentation.custom

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
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
        with(binding.webView){
            webViewClient = CustomWebViewClient(this.context)
            webChromeClient = WebChromeClient() // 안정성을 위해서 크로미움 클라이언트도 적용
            with(settings){
                javaScriptEnabled = true
                // 만개의 레시피 웹페이자 내부가 javaScript를 통한
                //동작 동적이 있음 따라서 ture로 설정
                allowContentAccess = true
                // content URI 사용을 위함
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                // 쿠팡링크등 Dynamic link 허용을 위함
            }
            loadUrl(link.toString())
        }

        binding.webviewAppbar.setNavigationOnClickListener {
            finish()
        }
    }


    override fun onBackPressed() {
        // 웹뷰내에서 여러 링크를 오갔을때 backbutton누르면 바로 종료가 아닌 브라우저처럼 동작
        with(binding.webView){
            val list = this.copyBackForwardList();
            if (list.currentIndex >= 0 && !(this.canGoBack())) {
                super.onBackPressed();
                // 처음 들어온 페이지이거나, history가 없는경우
            } else {
                // history가 있는 경우
                // 현재 페이지로 부터 history 수 만큼 뒷 페이지로 이동
                this.goBackOrForward(-(list.currentIndex));
                // history 삭제
                this.clearHistory();
            }
        }
    }


}

