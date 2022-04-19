package com.example.sopt_seminar.domain.usecase

import com.example.sopt_seminar.data.model.User
import com.example.sopt_seminar.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<User> = userRepository.getUser()
}