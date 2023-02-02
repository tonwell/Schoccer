package io.well.schoccer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.well.schoccer.domain.Schedule
import io.well.schoccer.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(private val repo: Repository): ViewModel() {
    private var _schedulesStateFlow: MutableStateFlow<List<Schedule>?> = MutableStateFlow(null)
    val schedulesStateFlow: StateFlow<List<Schedule>?> get() = _schedulesStateFlow

    fun getSchedules() = viewModelScope.launch {
        repo.getSchedules().collect { schedules ->
            _schedulesStateFlow.update { schedules }
        }
    }
}