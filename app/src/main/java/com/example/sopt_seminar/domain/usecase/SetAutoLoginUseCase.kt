package com.example.sopt_seminar.domain.usecase

import com.example.sopt_seminar.domain.repository.UserRepository
import javax.inject.Inject

class SetAutoLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(isAutoLogin: Boolean) = userRepository.setAutoLogin(isAutoLogin)
}