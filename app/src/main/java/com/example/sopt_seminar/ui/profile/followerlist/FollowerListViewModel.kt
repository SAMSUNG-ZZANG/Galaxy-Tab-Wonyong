package com.example.sopt_seminar.ui.profile.followerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sopt_seminar.domain.model.Follower
import com.example.sopt_seminar.domain.state.Result
import com.example.sopt_seminar.domain.usecase.GetFollowerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FollowerListViewModel @Inject constructor(
    private val getFollowerListUseCase: GetFollowerListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            when (val result = getFollowerListUseCase()) {
                is Result.Success<*> -> {
                    _uiState.update { state ->
                        state.copy(followerList = result.data as List<Follower>)
                    }
                }
                is Result.Fail<*> -> {
                    _uiState.update { state ->
                        state.copy(
                            error = true,
                            errorMsg = result.msg.toString()
                        )
                    }
                }
            }
        }
    }
}

data class UiState(
    val error: Boolean = false,
    val errorMsg: String = "",
    val followerList: List<Follower> = emptyList(),
)