package com.example.sopt_seminar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sopt_seminar.data.model.User
import com.example.sopt_seminar.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _user = MutableLiveData(User())
    private val user: LiveData<User> = _user

    suspend fun checkInput(idText: String, passwordText: String): Int {
        viewModelScope.launch {
            userRepository.getUser().collect {
                _user.value = it
            }
        }
        delay(100)
        val answer = when {
            idText.isEmpty() -> 0
            passwordText.isEmpty() -> 1
            !Pattern.matches("^[a-zA-Z0-9]*\$", idText) -> 2
            !Pattern.matches(
                "^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#\$%^&*])(?=.*[0-9!@#\$%^&*]).{8,15}\$",
                passwordText
            ) -> 3
            (user.value!!.userId == idText && user.value!!.userPassword == passwordText) -> 5
            else -> 4
        }
        return answer
    }
}