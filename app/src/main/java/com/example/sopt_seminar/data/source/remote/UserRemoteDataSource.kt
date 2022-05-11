package com.example.sopt_seminar.data.source.remote

import com.example.sopt_seminar.data.api.response.CommonResponse
import com.example.sopt_seminar.data.api.response.DataResponse
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun signUpUser(
        userName: String,
        userEmail: String,
        userPassword: String
    ): Response<CommonResponse<DataResponse.SignUp>>

    suspend fun signInUser(
        userEmail: String,
        userPassword: String,
    ): Response<CommonResponse<DataResponse.SignIn>>
}