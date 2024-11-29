package com.example.snoozeloo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snoozeloo.data.AlarmsRepository
import com.example.snoozeloo.data.AppUiState
import com.example.snoozeloo.data.SnoozelooAlarm
import com.example.snoozeloo.data.SnoozelooAlarmEvents
import com.example.snoozeloo.data.WeekDays
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            SnoozelooAlarmEvents.CancelAlarm -> {
                _uiState.update {
                    it.copy(createAlarm = SnoozelooAlarm())
                }
            }

            SnoozelooAlarmEvents.CancelAlarmRingtone -> {}
            SnoozelooAlarmEvents.CreateAlarm -> {}
            SnoozelooAlarmEvents.SaveAlarm -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        repository.createAlarm(alarm = _uiState.value.createAlarm)
                    }
                }
            }

            SnoozelooAlarmEvents.SaveAlarmNameDialog -> {}
            is SnoozelooAlarmEvents.SelectAlarmRingtone -> {}
            is SnoozelooAlarmEvents.SetAlarmIsEnabled -> {
                _uiState.update {
                    it.copy(createAlarm = it.createAlarm.copy(isEnabled = event.isEnabled))
                }
            }

            is SnoozelooAlarmEvents.SetAlarmNameDialogText -> {
                _uiState.update {
                    it.copy(createAlarm = it.createAlarm.copy(name = event.text))
                }
            }

            is SnoozelooAlarmEvents.SetVibrateIsEnabled -> {
                _uiState.update {
                    it.copy(createAlarm = it.createAlarm.copy(vibrate = event.vibrateEnabled))
                }
            }

            SnoozelooAlarmEvents.ShowAlarmNameDialog -> {}
            is SnoozelooAlarmEvents.ShowAlarmTrigger -> {}
            is SnoozelooAlarmEvents.SetRepeatDay -> {
                _uiState.update {
                    it.copy(
                        createAlarm = it.createAlarm.copy(
                            repeatDays =
                            if (event.isDaySelected) {
                                WeekDays.selectDay(it.createAlarm.repeatDays, event.dayText)
                            } else {
                                WeekDays.deselectDay(it.createAlarm.repeatDays, event.dayText)
                            }
                        )
                    )
                }
            }

            SnoozelooAlarmEvents.SnoozeAlarm -> {}
            SnoozelooAlarmEvents.TurnOffAlarm -> {}
            is SnoozelooAlarmEvents.SetAlarmTimeHours -> {
                _uiState.update {
                    it.copy(createAlarm = it.createAlarm.copy(timeHours = event.hours))
                }
            }

            is SnoozelooAlarmEvents.SetAlarmTimeMinutes -> {
                _uiState.update {
                    it.copy(createAlarm = it.createAlarm.copy(timeMinutes = event.minutes))
                }
            }

            is SnoozelooAlarmEvents.SetAlarmVolume -> {
                _uiState.update {
                    it.copy(createAlarm = it.createAlarm.copy(alarmVolume = event.alarmVolume))
                }
            }
        }
    }
}