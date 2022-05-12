package com.example.sopt_seminar.data.repository

import android.util.Log
import com.example.sopt_seminar.data.api.response.ErrorResponse
import com.example.sopt_seminar.data.entity.UserEntity
import com.example.sopt_seminar.data.entity.toFollower
import com.example.sopt_seminar.data.entity.toUser
import com.example.sopt_seminar.data.source.local.UserLocalDatSource
import com.example.sopt_seminar.data.source.remote.UserRemoteDataSource
import com.example.sopt_seminar.domain.model.Follower
import com.example.sopt_seminar.domain.model.User
import com.example.sopt_seminar.domain.repository.UserRepository
import com.example.sopt_seminar.domain.state.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl @Inject constructor(
    private val userLocalDatSource: UserLocalDatSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    private val gson = Gson()
    private val type = object : TypeToken<ErrorResponse>() {}.type

    override suspend fun getUser(): Flow<User> {
        return userLocalDatSource.getUser().map { userEntity ->
            userEntity.toUser()
        }
    }

    override suspend fun setUser(user: User) {
        userLocalDatSource.setUser(UserEntity(user.userName, user.userEmail, user.userPassword))
    }

    override suspend fun signUp(userName: String, userEmail: String, userPassword: String): Result {
        val response = userRemoteDataSource.signUpUser(userName, userEmail, userPassword)

        return if (response.isSuccessful) {
            val msg = "성공 ${response.body()?.message} 코드: ${response.body()?.status}"
            Result.Success(msg)
        } else {
            val errorResponse: ErrorResponse? =
                gson.fromJson(response.errorBody()!!.charStream(), type)
            val msg = "실패 ${errorResponse?.message} 코드: ${errorResponse?.status}"
            Result.Fail(msg)
        }
    }

    override suspend fun signIn(userEmail: String, userPassword: String): Result {
        val response = userRemoteDataSource.signInUser(userEmail, userPassword)

        return if (response.isSuccessful) {
            val msg = "성공 ${response.body()?.message} 코드: ${response.body()?.status}"
            Result.Success(msg)
        } else {
            val errorResponse: ErrorResponse? =
                gson.fromJson(response.errorBody()!!.charStream(), type)
            val msg = "실패 ${errorResponse?.message} 코드: ${errorResponse?.status}"
            Result.Fail(msg)
        }
    }

    override suspend fun getFollowerList(): Result {
        val response = userRemoteDataSource.getFollowerList()

        return if (response.isSuccessful) {
            val followerList: List<Follower> = response.body()?.map { followerEntity ->
                followerEntity.toFollower()
            }!!
            Log.d("resrsrsfalkgm", "$followerList")
            Result.Success(followerList)
        } else {
            Log.d("resrsrsfalkgm", response.errorBody()?.string().toString())
            Result.Fail(response.errorBody())
        }
    }
}