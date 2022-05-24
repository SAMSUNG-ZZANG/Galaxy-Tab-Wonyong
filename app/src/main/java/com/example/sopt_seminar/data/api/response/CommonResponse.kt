package com.example.sopt_seminar.data.api.response

data class CommonResponse<T>(
    val status: Int,
    val message: String,
    val success: String,
    val data: T
)