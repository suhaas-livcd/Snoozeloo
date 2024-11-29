package com.example.snoozeloo.data

data class AppUiState(
    val isLoading: Boolean = false,
    val alarms: List<SnoozelooAlarm> = emptyList(),
    val createAlarm: SnoozelooAlarm = SnoozelooAlarm(),
    val errorMessage: String? = null
)