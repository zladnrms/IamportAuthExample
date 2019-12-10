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
import androidx.lifecycle.viewModelScope
import com.papricacare.towndoctor.network.RetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val smsAuthApi by lazy {
        RetrofitInterface.createForImport()
    }

    private suspend fun iamportGetAuthProfile(access_token: String, auth_user_imp_uid: String) {
        smsAuthApi.getAuthProfile(access_token, auth_user_imp_uid).apply {
            this.response?.let {
                it.name?.let { name ->
                }
                it.phone?.let { phone ->
                }
            }
        }
    }

    fun iamportPostAccessToken(imp_key: String, imp_secret: String, auth_user_imp_uid: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    smsAuthApi.postAccessToken(imp_key, imp_secret).apply {
                        this.response?.access_token?.let {
                            iamportGetAuthProfile(it, auth_user_imp_uid)
                        }
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}
