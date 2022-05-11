package com.example.sopt_seminar.data.repository

import com.example.sopt_seminar.data.entity.UserEntity
import com.example.sopt_seminar.data.source.local.UserLocalDatSource
import com.example.sopt_seminar.domain.model.User
import com.example.sopt_seminar.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl @Inject constructor(
    private val userLocalDatSource: UserLocalDatSource
) : UserRepository {
    override suspend fun getUser(): Flow<User> {
        return userLocalDatSource.getUser().map { user ->
            User(user.name, user.email, user.password)
        }
    }

    override suspend fun setUser(user: User) {
        userLocalDatSource.setUser(UserEntity(user.userName, user.userEmail, user.userPassword))
    }
}