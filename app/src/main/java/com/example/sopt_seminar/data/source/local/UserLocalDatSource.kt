package com.example.sopt_seminar.data.source.local

import com.example.sopt_seminar.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserLocalDatSource {
    suspend fun getUser(): Flow<UserEntity>
    suspend fun setUser(user: UserEntity)
}