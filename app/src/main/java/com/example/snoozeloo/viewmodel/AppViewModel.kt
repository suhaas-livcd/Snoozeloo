package com.example.snoozeloo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snoozeloo.data.AlarmsRepository
import com.example.snoozeloo.data.AppUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: AlarmsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        fetchAlarms()
        repository
    }

    fun fetchAlarms() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            delay(2000)
            _uiState.value = uiState.value.copy(isLoading = false)
        }
    }
}