package com.example.sopt_seminar.data.api.response

data class ErrorResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
)