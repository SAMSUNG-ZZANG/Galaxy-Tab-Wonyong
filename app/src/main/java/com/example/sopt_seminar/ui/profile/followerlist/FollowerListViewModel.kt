package com.example.sopt_seminar.ui.profile.followerlist

import android.util.Log
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
    private val _eventFlow = MutableSharedFlow<FollowerListEvent>(replay = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            getFollowerListUseCase().collect { result ->
                when (result) {
                    is Result.Uninitialized -> {
                        Log.d("asdfasdfs","viewmodel Uninitialized")
                        emitEvent(FollowerListEvent.Loading)
                    }
                    is Result.Success<*> -> {
                        Log.d("asdfasdfs","viewmodel Success")
                        emitEvent(FollowerListEvent.FollowerList(result.data as List<Follower>))
                    }
                    is Result.Fail<*> -> {
                        emitEvent(FollowerListEvent.ShowToast(result.msg.toString()))
                    }
                }
            }
        }
    }

    private fun emitEvent(event: FollowerListEvent) {
        viewModelScope.launch {
            Log.d("asdfasdfs","viewmodel emit")
            _eventFlow.emit(event)
        }
    }
}

sealed class FollowerListEvent {
    object Loading : FollowerListEvent()
    data class ShowToast(val msg: String) : FollowerListEvent()
    data class FollowerList(val data: List<Follower>) : FollowerListEvent()
}