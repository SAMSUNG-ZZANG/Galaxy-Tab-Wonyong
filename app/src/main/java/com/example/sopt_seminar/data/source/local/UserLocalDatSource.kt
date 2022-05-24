package com.example.sopt_seminar.data.source.local

import kotlinx.coroutines.flow.Flow

interface UserLocalDatSource {
    suspend fun isAutoLogin(): Flow<Boolean>
    suspend fun setAutoLogin(isAutoLogin: Boolean)
}