package com.example.sopt_seminar.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sopt_seminar.domain.state.Result
import com.example.sopt_seminar.domain.usecase.SetUserUseCase
import com.example.sopt_seminar.domain.usecase.ValidateTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateTextUseCase: ValidateTextUseCase,
    private val setUserUseCase: SetUserUseCase
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
        when (val result =
            validateTextUseCase(idText.value, pwText.value, nameText.value)) {
            is Result.Fail -> {
                _errorMsg.value = result.msg
                _isError.value = true
            }
            is Result.Success -> {
                viewModelScope.launch {
                    _isError.value = false
                    setUserUseCase(nameText.value, idText.value, pwText.value)
                    _isFinish.value = true
                }
            }
        }
    }
}