package com.example.sopt_seminar.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sopt_seminar.domain.state.Result
import com.example.sopt_seminar.domain.usecase.SignUpUseCase
import com.example.sopt_seminar.domain.usecase.ValidateTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateTextUseCase: ValidateTextUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _nameText = MutableStateFlow("")
    private val _idText = MutableStateFlow("")
    private val _pwText = MutableStateFlow("")
    private val _errorMsg: MutableStateFlow<String> = MutableStateFlow("")
    private val _isError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _isFinish: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val nameText get() = _nameText
    val idText get() = _idText
    val pwText get() = _pwText
    val errorMsg = _errorMsg.asStateFlow()
    val isError = _isError.asStateFlow()
    val isFinish = _isFinish.asStateFlow()

    fun checkInput() {
        viewModelScope.launch {
            _isError.value = false
            when (val result =
                validateTextUseCase(idText.value, pwText.value, nameText.value)) {
                is Result.Fail<*> -> {
                    _errorMsg.value = result.msg.toString()
                    _isError.value = true
                }
                is Result.Success<*> -> {
                    when (val response =
                        signUpUseCase(nameText.value, idText.value, pwText.value)) {
                        is Result.Success<*> -> {
                            _errorMsg.value = response.data.toString()
                            _isError.value = true
                            delay(50)
                            _isFinish.value = true
                        }
                        is Result.Fail<*> -> {
                            _errorMsg.value = response.msg.toString()
                            _isError.value = true
                        }
                    }
                }
            }
        }
    }
}