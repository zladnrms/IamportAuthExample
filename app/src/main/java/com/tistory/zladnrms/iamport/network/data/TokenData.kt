package com.papricacare.towndoctor.network.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TokenData(
    @SerializedName("access_token") var access_token: String? = null,
    @SerializedName("now") var now: Int = 0,
    @SerializedName("expired_at") var expired_at: Int = 0
)
