package com.example.sopt_seminar.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sopt_seminar.domain.state.Result
import com.example.sopt_seminar.domain.usecase.SignUpUseCase
import com.example.sopt_seminar.domain.usecase.ValidateTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateTextUseCase: ValidateTextUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _nameText = MutableStateFlow("")
    private val _idText = MutableStateFlow("")
    private val _pwText = MutableStateFlow("")
    private val _eventFlow = MutableSharedFlow<Event>()

    val eventFlow = _eventFlow.asSharedFlow()
    val nameText get() = _nameText
    val idText get() = _idText
    val pwText get() = _pwText

    fun checkInput() {
        viewModelScope.launch {
            when (val result =
                validateTextUseCase(idText.value, pwText.value, nameText.value)) {
                is Result.Fail<*> -> {
                    emitEvent(Event.ShowToast(result.msg.toString()))
                }
                is Result.Success<*> -> {
                    when (val response =
                        signUpUseCase(nameText.value, idText.value, pwText.value)) {
                        is Result.Success<*> -> {
                            emitEvent(Event.ShowToast(response.data.toString()))
                            emitEvent(Event.IsFinish)
                        }
                        is Result.Fail<*> -> {
                            emitEvent(Event.ShowToast(response.msg.toString()))
                        }
                    }
                }
            }
        }
    }

    private fun emitEvent(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }
}

sealed class Event {
    data class ShowToast(val msg: String) : Event()
    object IsFinish : Event()
}