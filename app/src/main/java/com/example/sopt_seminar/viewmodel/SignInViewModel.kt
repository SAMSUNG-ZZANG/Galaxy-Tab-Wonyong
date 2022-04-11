package com.example.sopt_seminar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sopt_seminar.data.model.User
import com.example.sopt_seminar.data.repository.UserRepository
import com.example.sopt_seminar.domain.state.Event
import com.example.sopt_seminar.domain.state.Result
import com.example.sopt_seminar.domain.usecase.ValidateTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val validateTextUseCase: ValidateTextUseCase
) : ViewModel() {
    private val _user = MutableLiveData(User())
    private val user: LiveData<User> = _user
    private val _showErrorToast = MutableLiveData<Event<Boolean>>()
    val showErrorToast: LiveData<Event<Boolean>> = _showErrorToast
    private var _errorMsg: MutableLiveData<String> = MutableLiveData("")
    val errorMsg get() = _errorMsg
    private var _isError: MutableLiveData<Boolean> = MutableLiveData(true)
    val isError get() = _isError

    suspend fun checkInput(idText: String, passwordText: String) {
        viewModelScope.launch {
            userRepository.getUser().collect {
                _user.value = it
            }
            when(val result = validateTextUseCase(idText, passwordText)){
                is Result.Fail -> {
                    Log.d("SignInViewModelsss",result.toString())
                    _errorMsg.value = result.msg
                    _showErrorToast.value = Event(true)
                    _isError.value = true
                }
                is Result.Success -> {
                    _isError.value = false
                    Log.d("SignInViewModelsss",result.toString())
                }
            }
        }
    }
}