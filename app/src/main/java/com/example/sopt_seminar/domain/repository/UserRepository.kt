package com.example.sopt_seminar.domain.repository

import com.example.sopt_seminar.domain.model.User
import com.example.sopt_seminar.domain.state.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): Flow<User>
    suspend fun setUser(user: User)
    suspend fun signUp(
        userName: String,
        userEmail: String,
        userPassword: String
    ): Result

    suspend fun signIn(
        userEmail: String,
        userPassword: String,
    ): Result

    suspend fun getFollowerList(): Result
}