package com.example.sopt_seminar.data.api.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
)