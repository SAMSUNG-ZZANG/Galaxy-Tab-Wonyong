package com.example.sopt_seminar.domain.usecase

import com.example.sopt_seminar.domain.model.User
import com.example.sopt_seminar.domain.repository.UserRepository
import javax.inject.Inject

class SetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nameText: String, userId: String, userPassword: String) {
        userRepository.setUser(User(nameText, userId, userPassword))
    }
}