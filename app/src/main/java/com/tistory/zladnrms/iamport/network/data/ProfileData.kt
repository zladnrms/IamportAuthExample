package com.papricacare.towndoctor.network.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProfileData(
    @SerializedName("birth") var birth: Int = 0,
    @SerializedName("certified") var certified: Boolean = true,
    @SerializedName("certified_at") var certified_at: Int = 0,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("phone") var phone: String? = null
)
