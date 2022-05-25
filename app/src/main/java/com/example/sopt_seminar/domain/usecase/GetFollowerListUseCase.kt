package com.example.sopt_seminar.domain.usecase

import com.example.sopt_seminar.domain.model.Follower
import com.example.sopt_seminar.domain.repository.UserRepository
import javax.inject.Inject

class GetFollowerListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<Follower>> = userRepository.getFollowerList()
}