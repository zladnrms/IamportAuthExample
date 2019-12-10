package com.tistory.zladnrms.iamport.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.papricacare.towndoctor.ui.smsauth.MainViewModel
import com.papricacare.towndoctor.ui.smsauth.SmsAuthActivity
import com.tistory.zladnrms.iamport.R
import com.tistory.zladnrms.iamport.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val SMS_AUTH_REQ_CODE = 202

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        button_to_auth.setOnClickListener {
            startActivityForResult(SmsAuthActivity.starterIntent(this, "웹뷰 연동 HTML 파일 주소"),
                SMS_AUTH_REQ_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SMS_AUTH_REQ_CODE) {
            data?.let {
                it.getStringExtra("imp_uid")?.let { impUid ->
                    viewModel.iamportPostAccessToken(
                        getString(R.string.imp_key),
                        getString(R.string.imp_secret),
                        impUid)
                } ?: Toast.makeText(this, "인증 결과가 누락되었습니다.\n재인증이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
