package com.example.sopt_seminar.domain.state

sealed class Result {
    object Uninitialized : Result()
    data class Success<T>(val data: T) : Result()
    data class Fail<T>(val msg: T) : Result()
}