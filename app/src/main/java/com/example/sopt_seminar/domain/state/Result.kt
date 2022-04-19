package com.example.sopt_seminar.domain.state

sealed class Result {
    object Uninitialized : Result()

    object Success : Result()
    data class Fail(val msg: String) : Result()
}