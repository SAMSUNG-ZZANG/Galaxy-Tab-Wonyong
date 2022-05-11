package com.example.sopt_seminar.data.entity

import com.example.sopt_seminar.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("password") val password: String = "",
)

fun UserEntity.toUser() = User(
    userName = name,
    userEmail = email,
    userPassword = password
)

fun User.toUserEntity() = UserEntity(
    name = userName,
    email = userEmail,
    password = userPassword
)