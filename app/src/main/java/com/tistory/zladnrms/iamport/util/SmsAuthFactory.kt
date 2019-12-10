package com.papricacare.towndoctor.ui.smsauth.util

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.webkit.JavascriptInterface

class SmsAuthFactory(activity: Activity) {
    private val TAG = "SmsAuthFactory★"

    private val activity = activity

    @JavascriptInterface
    fun resultAuth(message: String, impUid: String?) {
        val intent = Intent()

        if (message == "success" && impUid != null) {
            intent.putExtra("result", "success")
            intent.putExtra("imp_uid", impUid)
            activity.setResult(RESULT_OK, intent)
            activity.finish()
        } else {
            intent.putExtra("result", "failure")
            activity.setResult(RESULT_OK, intent)
            activity.finish()
        }
    }
}
