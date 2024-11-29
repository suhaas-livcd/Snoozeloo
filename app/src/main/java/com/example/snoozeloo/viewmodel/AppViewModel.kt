package com.example.snoozeloo.viewmodel

import android.database.Cursor
import android.media.RingtoneManager
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snoozeloo.AlarmScheduler
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: AlarmsRepository,
    private val alarmScheduler: AlarmScheduler,
    private val ringtoneManager: RingtoneManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()
    private var cursor: Cursor? = null

    init {
        // Fetch data from the repository
        fetchAlarms()
    }

    private fun fetchAlarms() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.fetchAlarms().collect { alarms ->
                _uiState.update { it.copy(isLoading = false, alarms = alarms) }
                alarms.forEach { alarm ->
                    if (alarm.isEnabled) {
                        alarmScheduler.scheduleAlarm(alarm)
                    }
                }
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
                        repository.upsertAlarm(alarm = _uiState.value.createAlarm)
                    }
                }
            }

            SnoozelooAlarmEvents.SaveAlarmNameDialog -> {}
            is SnoozelooAlarmEvents.SelectAlarmRingtone -> {
                _uiState.update {
                    it.copy(
                        createAlarm = it.createAlarm.copy(
                            alarmRingtoneTitle = event.ringtoneInfo.first,
                            alarmRingtoneURI = event.ringtoneInfo.second.toString()
                        )
                    )
                }

            }

            is SnoozelooAlarmEvents.SetAlarmIsEnabled -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        repository.upsertAlarm(alarm = _uiState.value.alarms.find {
                            it.id == event.alarmId
                        }!!.copy(isEnabled = event.isEnabled))
                    }
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

            SnoozelooAlarmEvents.ClickOnAlarmRingtone -> {
                Timber.d("Clicked Ringtones")
                if (cursor == null) {
                    ringtoneManager.setType(RingtoneManager.TYPE_ALARM)
                }
                // Get a cursor for available ringtones
                cursor = ringtoneManager.cursor

                // Loop through the cursor and fetch ringtone details
                val ringtoneList: MutableList<Pair<String, Uri>> = mutableListOf()
                // Adding silent
                ringtoneList.add(Pair(first = "Silent", second = Uri.EMPTY))
                cursor?.let {
                    while (it.moveToNext()) {
                        val title = it.getString(RingtoneManager.TITLE_COLUMN_INDEX)
                        val uri = ringtoneManager.getRingtoneUri(it.position)
                        ringtoneList.add(
                            Pair(
                                first = title,
                                second = uri
                            )
                        )
                    }
                }

                _uiState.update {
                    it.copy(ringtoneList = ringtoneList)
                }
            }
        }
    }
}