package com.example.sopt_seminar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sopt_seminar.data.model.User
import com.example.sopt_seminar.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    fun checkInput(idText: String, passwordText: String, nameText: String): Int {
        return when {
            nameText.isEmpty() -> 0
            idText.isEmpty() -> 1
            passwordText.isEmpty() -> 2
            !Pattern.matches("^[a-zA-Z0-9]*\$", idText) -> 3
            !Pattern.matches("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#\$%^&*])(?=.*[0-9!@#\$%^&*]).{8,15}\$", passwordText) -> 4
            !Pattern.matches("^[가-힣]*\$", nameText) -> 5
            else -> {
                viewModelScope.launch {
                    setUser(idText, passwordText)
                    setIsUser()
                }
                6
            }
        }
    }

    private suspend fun setUser(userId: String, userPassword: String) {
        userRepository.setUser(User(userId, userPassword))
    }

    private suspend fun setIsUser() {
        userRepository.setIsUser()
    }
}