package com.example.sopt_seminar.ui.profile.followerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sopt_seminar.domain.model.Follower
import com.example.sopt_seminar.domain.state.Result
import com.example.sopt_seminar.domain.usecase.GetFollowerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FollowerListViewModel @Inject constructor(
    private val getFollowerListUseCase: GetFollowerListUseCase
) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<FollowerListEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            when (val result = getFollowerListUseCase()) {
                is Result.Success<*> -> {
                    emitEvent(FollowerListEvent.FollowerList(result.data as List<Follower>))
                }
                is Result.Fail<*> -> {
                    emitEvent(FollowerListEvent.ShowToast(result.msg.toString()))
                }
            }
        }
    }

    private fun emitEvent(event: FollowerListEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }
}

sealed class FollowerListEvent {
    data class ShowToast(val msg: String) : FollowerListEvent()
    data class FollowerList(val data: List<Follower>) : FollowerListEvent()
}