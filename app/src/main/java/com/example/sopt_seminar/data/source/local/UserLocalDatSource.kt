package com.example.sopt_seminar.data.source.local

import com.example.sopt_seminar.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDatSource {
    fun getUser(): Flow<User>
    suspend fun setUser(user: User)
    fun getIsUser(): Flow<Boolean>
    suspend fun setIsUser()
}