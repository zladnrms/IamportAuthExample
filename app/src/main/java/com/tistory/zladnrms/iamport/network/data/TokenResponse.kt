package com.papricacare.towndoctor.network.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TokenResponse(
    @SerializedName("code") var code: Int = 0,
    @SerializedName("message") var message: String? = null,
    @SerializedName("response") var response: TokenData? = null
)
