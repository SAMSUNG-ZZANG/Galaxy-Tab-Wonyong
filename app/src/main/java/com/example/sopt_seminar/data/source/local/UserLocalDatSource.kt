package com.example.sopt_seminar.data.source.local

import com.example.sopt_seminar.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDatSource {
    suspend fun getUser(): Flow<User>
    suspend fun setUser(user: User)
}