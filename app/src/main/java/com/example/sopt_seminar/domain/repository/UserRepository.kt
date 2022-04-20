package com.example.sopt_seminar.domain.repository

import com.example.sopt_seminar.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): Flow<User>
    suspend fun setUser(user: User)
}