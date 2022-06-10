package com.example.sopt_seminar.data.source.remote

import com.example.sopt_seminar.data.api.ApiService
import com.example.sopt_seminar.data.api.request.SignInRequest
import com.example.sopt_seminar.data.api.request.SignUpRequest
import com.example.sopt_seminar.data.api.response.CommonResponse
import com.example.sopt_seminar.data.api.response.DataResponse
import com.example.sopt_seminar.data.entity.FollowerEntity
import com.example.sopt_seminar.di.BaseApi
import com.example.sopt_seminar.di.GithubApi
import javax.inject.Inject
import retrofit2.Response

class UserRemoteDataSourceImpl @Inject constructor(
    @BaseApi private val baseApiService: ApiService,
    @GithubApi private val githubApiService: ApiService
) : UserRemoteDataSource {
    override suspend fun signUpUser(
        userName: String,
        userEmail: String,
        userPassword: String
    ): CommonResponse<DataResponse.SignUp> {
        return baseApiService.signUp(
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
    ): CommonResponse<DataResponse.SignIn> {
        return baseApiService.signIn(
            SignInRequest(
                email = userEmail,
                password = userPassword
            )
        )
    }

    override suspend fun getFollowerList(): Response<List<FollowerEntity>> {
        return githubApiService.getFollowerList()
    }
}