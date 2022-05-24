package com.example.sopt_seminar.data.api.response

sealed class DataResponse {
    data class SignIn(
        val email: String,
        val name: String,
    ) : DataResponse()

    data class SignUp(
        val id: Int
    ) : DataResponse()
}