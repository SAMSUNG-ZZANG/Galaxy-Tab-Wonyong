package com.example.sopt_seminar.data.api.response

import com.google.gson.annotations.SerializedName

data class CommonResponse<T>(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: String,
    @SerializedName("data") val data: T
)