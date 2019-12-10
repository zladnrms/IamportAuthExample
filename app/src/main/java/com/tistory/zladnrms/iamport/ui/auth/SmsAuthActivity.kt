package com.papricacare.towndoctor.ui.smsauth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.papricacare.towndoctor.ui.smsauth.util.SmsAuthFactory
import com.tistory.zladnrms.iamport.R
import com.tistory.zladnrms.iamport.databinding.ActivitySmsAuthBinding
import kotlinx.android.synthetic.main.activity_sms_auth.*

class SmsAuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySmsAuthBinding
    private lateinit var viewModel: SmsAuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SmsAuthViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sms_auth)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initWebView()
    }

    private fun initWebView() {
        /** ViewModel에 웹뷰를 초기화 */
        viewModel.applyWebView(webView)

        /** 웹 뷰 URL 설정 */
        val url = intent.getStringExtra("webview_url")
        viewModel.setUrl(url)

        /** 웹 뷰에 자바스크립트와 안드로이드 연동 가능하도록 설정 */
        webView.addJavascriptInterface(
            SmsAuthFactory(
                this
            ), "AndroidBridge")
    }

    /**
     *  웹뷰 백버튼 클릭 시 history back 가능하도록 처리
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        viewModel.webView.apply {
            if ((keyCode == KeyEvent.KEYCODE_BACK) && this.canGoBack()) {
                this.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        fun starterIntent(context: Context, url: String): Intent {
            return Intent(context, SmsAuthActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra("webview_url", url)
            }
        }
    }
}
