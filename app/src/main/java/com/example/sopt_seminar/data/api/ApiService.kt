package com.example.sopt_seminar.data.api

import com.example.sopt_seminar.data.api.request.SignInRequest
import com.example.sopt_seminar.data.api.request.SignUpRequest
import com.example.sopt_seminar.data.api.response.CommonResponse
import com.example.sopt_seminar.data.api.response.DataResponse
import com.example.sopt_seminar.data.constants.GITHUB_USER_FOLLOWERS
import com.example.sopt_seminar.data.constants.SIGN_IN
import com.example.sopt_seminar.data.constants.SIGN_UP
import com.example.sopt_seminar.data.entity.FollowerEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST(SIGN_UP)
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<CommonResponse<DataResponse.SignUp>>

    @POST(SIGN_IN)
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): Response<CommonResponse<DataResponse.SignIn>>

    @GET(GITHUB_USER_FOLLOWERS)
    suspend fun getFollowerList(
        @Path("username") userName: String = "KWY0218",
    ): Response<List<FollowerEntity>>
}