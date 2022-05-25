package com.example.sopt_seminar.data.source.remote

import com.example.sopt_seminar.data.api.response.CommonResponse
import com.example.sopt_seminar.data.api.response.DataResponse
import com.example.sopt_seminar.data.entity.FollowerEntity
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun signUpUser(
        userName: String,
        userEmail: String,
        userPassword: String
    ): CommonResponse<DataResponse.SignUp>

    suspend fun signInUser(
        userEmail: String,
        userPassword: String,
    ): CommonResponse<DataResponse.SignIn>

    suspend fun getFollowerList(): Response<List<FollowerEntity>>
}