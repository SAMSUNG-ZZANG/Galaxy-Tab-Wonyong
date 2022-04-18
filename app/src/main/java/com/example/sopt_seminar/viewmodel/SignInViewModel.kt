package com.example.sopt_seminar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sopt_seminar.data.model.User
import com.example.sopt_seminar.util.Event
import com.example.sopt_seminar.domain.state.Result
import com.example.sopt_seminar.domain.usecase.GetUserUseCase
import com.example.sopt_seminar.domain.usecase.ValidateTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
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

    init {
        viewModelScope.launch {
            getUserUseCase().collect{
                _user.value = it
            }
        }
    }

    fun checkInput(idText: String, passwordText: String) {
        viewModelScope.launch {
            when (val result = validateTextUseCase(idText, passwordText)) {
                is Result.Fail -> {
                    _errorMsg.value = result.msg
                    _showErrorToast.value = Event(true)
                    _isError.value = true
                }
                is Result.Success -> {
                    if(user.value!!.userId == idText && user.value!!.userPassword == passwordText) _isError.value = false
                    else {
                        _errorMsg.value = "아이디 비밀번호를 다시 입력해 주세요"
                        _showErrorToast.value = Event(true)
                        _isError.value = true
                    }
                }
            }
        }
    }
}