package com.example.sopt_seminar.domain.usecase

import com.example.sopt_seminar.domain.repository.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userEmail: String,
        userPassword: String
    ): Result<String> = userRepository.signIn(
        userEmail,
        userPassword
    )
}