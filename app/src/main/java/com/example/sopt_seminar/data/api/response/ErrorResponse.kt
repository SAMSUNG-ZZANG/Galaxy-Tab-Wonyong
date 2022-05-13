package com.example.sopt_seminar.data.api.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
)