package com.example.sopt_seminar.data.repository

import com.example.sopt_seminar.data.entity.toFollower
import com.example.sopt_seminar.data.source.local.UserLocalDatSource
import com.example.sopt_seminar.data.source.remote.UserRemoteDataSource
import com.example.sopt_seminar.domain.model.Follower
import com.example.sopt_seminar.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl @Inject constructor(
    private val userLocalDatSource: UserLocalDatSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun isAutoLogin(): Flow<Boolean> = userLocalDatSource.isAutoLogin()

    override suspend fun setAutoLogin(isAutoLogin: Boolean) {
        userLocalDatSource.setAutoLogin(isAutoLogin)
    }

    override suspend fun signUp(
        userName: String,
        userEmail: String,
        userPassword: String
    ): Result<String> {
        runCatching { userRemoteDataSource.signUpUser(userName, userEmail, userPassword) }
            .onSuccess { return Result.success("${it.success} message: ${it.message})") }
            .onFailure { return Result.failure(Throwable("이미 존재하는 유저")) }
        return Result.failure(Throwable("what the...f"))
    }

    override suspend fun signIn(userEmail: String, userPassword: String): Result<String> {
        runCatching { userRemoteDataSource.signInUser(userEmail, userPassword) }
            .onSuccess { return Result.success("${it.success} message: ${it.message})") }
            .onFailure { error ->
                return when (error.message) {
                    NOT_FOUND -> Result.failure(Throwable("유저 정보를 찾을 수 없습니다"))
                    else -> Result.failure(Throwable("비밀번호가 올바르지 않음"))
                }
            }
        return Result.failure(Throwable("what the...f"))
    }

    override suspend fun getFollowerList(): Result<List<Follower>> {
        runCatching { userRemoteDataSource.getFollowerList() }
            .onSuccess { response ->
                return Result.success(response.body()!!.map { followerEntity -> followerEntity.toFollower() })
            }
            .onFailure { return Result.failure(Throwable(it)) }
        return Result.failure(Throwable("what the...f"))
    }

    companion object {
        const val NOT_FOUND = "HTTP 404 Not Found"
    }
}