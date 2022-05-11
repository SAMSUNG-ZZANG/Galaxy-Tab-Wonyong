package com.example.sopt_seminar.domain.usecase

import com.example.sopt_seminar.domain.repository.UserRepository
import javax.inject.Inject
import com.example.sopt_seminar.domain.state.Result

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userEmail: String,
        userPassword: String
    ): Result = userRepository.signIn(
        userEmail,
        userPassword
    )
}