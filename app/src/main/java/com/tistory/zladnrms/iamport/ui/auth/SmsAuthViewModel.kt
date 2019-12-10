package com.papricacare.towndoctor.ui.smsauth

import android.annotation.SuppressLint
import android.app.Application
import android.net.http.SslError
import android.os.Build
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel

class SmsAuthViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var webView: WebView

    fun applyWebView(webView: WebView) {
        this.webView = webView
    }

    fun setUrl(url: String) {
        setWebViewSetting(this.webView, url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setWebViewSetting(webView: WebView, url: String) {
        webView.apply {
            // 자바스크립트 허용
            settings.javaScriptEnabled = true
            // 자바스크립트 window.open() 허용
            settings.javaScriptCanOpenWindowsAutomatically = true

            settings.setRenderPriority(WebSettings.RenderPriority.HIGH)

            // 캐시 사용하지 않도록 설정
            settings.cacheMode = WebSettings.LOAD_NO_CACHE

            webChromeClient = myChromeClient()
            webViewClient = WebViewClientClass()

            // 웹뷰 단위에서의 하드웨어 가속
            setLayerType(View.LAYER_TYPE_HARDWARE, null)

            // 스크롤 바 설정
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
            isScrollbarFadingEnabled = true

            if (Build.VERSION.SDK_INT >= 19) {
                setLayerType(View.LAYER_TYPE_HARDWARE, null)
            } else {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            }
            loadUrl(url)
        }
    }

    private inner class myChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            Log.d("onPRgress", "$newProgress")
            super.onProgressChanged(view, newProgress)
        }
    }

    private class WebViewClientClass : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url: String = request?.url.toString()
            view?.loadUrl(url)
            Log.d("Error : ", "$request")

            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            Log.d("Error : ", "${error?.primaryError}")
            handler?.proceed()
        }
    }
}
