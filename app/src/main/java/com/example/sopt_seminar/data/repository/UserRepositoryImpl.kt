package com.example.sopt_seminar.data.repository

import com.example.sopt_seminar.data.model.User
import com.example.sopt_seminar.data.source.local.UserLocalDatSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl @Inject constructor(
    private val userLocalDatSource: UserLocalDatSource
) : UserRepository {
    override suspend fun getUser(): Flow<User> {
        return userLocalDatSource.getUser()
    }

    override suspend fun setUser(user: User) {
        userLocalDatSource.setUser(user)
    }
}