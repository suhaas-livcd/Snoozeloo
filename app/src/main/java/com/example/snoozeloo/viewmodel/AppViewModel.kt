package com.example.snoozeloo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snoozeloo.data.AlarmsRepository
import com.example.snoozeloo.data.AppUiState
import com.example.snoozeloo.data.SnoozelooAlarmEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: AlarmsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        // Fetch data from the repository
        fetchAlarms()

        // Create alarm
        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                repository.createAlarm(alarm = SnoozelooAlarm(name = "Wake Up", time = "10:00", isEnabled = true, sound = "Default", repeatDays = 0))
//                repository.createAlarm(alarm = SnoozelooAlarm(name = "Education", time = "10:00", isEnabled = false, sound = "Default", repeatDays = 0))
//                repository.createAlarm(alarm = SnoozelooAlarm(name = "Dinner", time = "10:00", isEnabled = true, sound = "Default", repeatDays = 0))
//
//            }
        }
    }

    private fun fetchAlarms() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.fetchAlarms().collect { alarms ->
                _uiState.update { it.copy(isLoading = false, alarms = alarms) }
            }
        }
    }

    fun onEvent(event: SnoozelooAlarmEvents) {
        when (event) {
            SnoozelooAlarmEvents.CancelAlarm -> {}
            SnoozelooAlarmEvents.CancelAlarmRingtone -> {}
            SnoozelooAlarmEvents.CreateAlarm -> {}
            SnoozelooAlarmEvents.SaveAlarm -> {}
            SnoozelooAlarmEvents.SaveAlarmNameDialog -> {}
            is SnoozelooAlarmEvents.SelectAlarmRingtone -> {}
            is SnoozelooAlarmEvents.SetAlarmIsEnabled -> {}
            is SnoozelooAlarmEvents.SetAlarmNameDialogText -> {}
            SnoozelooAlarmEvents.ShowAlarmNameDialog -> {}
            is SnoozelooAlarmEvents.ShowAlarmTrigger -> {}
            SnoozelooAlarmEvents.SnoozeAlarm -> {}
            SnoozelooAlarmEvents.TurnOffAlarm -> {}
        }
    }
}