package com.example.sopt_seminar.data.source.remote

import com.example.sopt_seminar.data.api.ApiService
import com.example.sopt_seminar.data.api.request.SignInRequest
import com.example.sopt_seminar.data.api.request.SignUpRequest
import com.example.sopt_seminar.data.api.response.CommonResponse
import com.example.sopt_seminar.data.api.response.DataResponse
import javax.inject.Inject
import retrofit2.Response

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : UserRemoteDataSource {
    override suspend fun signUpUser(
        userName: String,
        userEmail: String,
        userPassword: String
    ): Response<CommonResponse<DataResponse.SignUp>> {
        return apiService.signUp(
            SignUpRequest(
                name = userName,
                email = userEmail,
                password = userPassword
            )
        )
    }

    override suspend fun signInUser(
        userEmail: String,
        userPassword: String,
    ): Response<CommonResponse<DataResponse.SignIn>> {
        return apiService.signIn(
            SignInRequest(
                email = userEmail,
                password = userPassword
            )
        )
    }
}