package com.example.sopt_seminar.data.repository

import com.example.sopt_seminar.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<User>
    suspend fun setUser(user: User)
    fun getIsUser(): Flow<Boolean>
    suspend fun setIsUser()
}