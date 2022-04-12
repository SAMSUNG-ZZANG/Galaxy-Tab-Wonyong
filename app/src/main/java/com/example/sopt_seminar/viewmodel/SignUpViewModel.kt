package com.example.sopt_seminar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sopt_seminar.data.model.User
import com.example.sopt_seminar.domain.state.Event
import com.example.sopt_seminar.domain.state.Result
import com.example.sopt_seminar.domain.usecase.SetUserUseCase
import com.example.sopt_seminar.domain.usecase.ValidateTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateTextUseCase:ValidateTextUseCase,
    private val setUserUseCase: SetUserUseCase
) : ViewModel() {
    private val _showErrorToast = MutableLiveData<Event<Boolean>>()
    val showErrorToast: LiveData<Event<Boolean>> = _showErrorToast

    private var _errorMsg: MutableLiveData<String> = MutableLiveData("")
    val errorMsg get() = _errorMsg

    private var _isError: MutableLiveData<Boolean> = MutableLiveData(true)
    val isError get() = _isError

    fun checkInput(idText: String, passwordText: String, nameText: String){
        when(val result = validateTextUseCase(idText, passwordText, nameText)){
            is Result.Fail -> {
                _errorMsg.value = result.msg
                _showErrorToast.value = Event(true)
                _isError.value = true
            }
            is Result.Success -> {
                viewModelScope.launch {
                    setUser(nameText, idText, passwordText)
                    _isError.value = false
                }
            }
        }
    }

    private suspend fun setUser(nameText: String,userId: String, userPassword: String) {
        setUserUseCase(User(nameText, userId, userPassword))
    }
}