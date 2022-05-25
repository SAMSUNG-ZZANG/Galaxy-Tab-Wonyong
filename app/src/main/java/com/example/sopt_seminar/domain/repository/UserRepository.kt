package com.example.sopt_seminar.domain.repository

import com.example.sopt_seminar.domain.model.Follower
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun isAutoLogin(): Flow<Boolean>
    suspend fun setAutoLogin(isAutoLogin: Boolean)
    suspend fun signUp(
        userName: String,
        userEmail: String,
        userPassword: String
    ): Result<String>

    suspend fun signIn(
        userEmail: String,
        userPassword: String,
    ): Result<String>

    suspend fun getFollowerList(): Result<List<Follower>>
}