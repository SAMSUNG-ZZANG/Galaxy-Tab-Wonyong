package com.example.sopt_seminar.domain.usecase

import com.example.sopt_seminar.domain.repository.UserRepository
import javax.inject.Inject
import com.example.sopt_seminar.domain.state.Result

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userName: String,
        userEmail: String,
        userPassword: String
    ): Result = userRepository.signUp(
        userName,
        userEmail,
        userPassword
    )
}