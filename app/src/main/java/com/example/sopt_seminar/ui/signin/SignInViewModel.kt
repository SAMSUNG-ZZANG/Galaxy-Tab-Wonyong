package com.example.sopt_seminar.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sopt_seminar.domain.state.Result
import com.example.sopt_seminar.domain.usecase.GetUserUseCase
import com.example.sopt_seminar.domain.usecase.ValidateTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val validateTextUseCase: ValidateTextUseCase
) : ViewModel() {
    private val _idText = MutableStateFlow("")
    private val _pwText = MutableStateFlow("")
    private val _errorMsg: MutableStateFlow<String> = MutableStateFlow("")
    private val _isError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _isFinish: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val idText get() = _idText
    val pwText get() = _pwText
    val errorMsg = _errorMsg.asStateFlow()
    val isError = _isError.asStateFlow()
    val isFinish = _isFinish.asStateFlow()

    fun checkInput() {
        viewModelScope.launch {
            when (val result = validateTextUseCase(idText.value, pwText.value)) {
                is Result.Fail<*> -> {
                    _errorMsg.value = result.msg.toString()
                    _isError.value = true
                }
                is Result.Success<*> -> {
                    getUserUseCase().collect { user->
                        if (user.userEmail == idText.value && user.userPassword == pwText.value) {
                            _isError.value = false
                            _isFinish.value = true
                        } else {
                            _errorMsg.value = "아이디 비밀번호를 다시 입력해 주세요"
                            _isError.value = true
                        }
                    }
                }
            }
        }
    }

    fun setText(id: String, pw: String) {
        _idText.value = id
        _pwText.value = pw
    }
}