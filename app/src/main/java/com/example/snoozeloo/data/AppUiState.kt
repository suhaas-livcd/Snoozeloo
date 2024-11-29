package com.example.snoozeloo.data

import android.net.Uri

data class AppUiState(
    val isLoading: Boolean = false,
    val alarms: List<SnoozelooAlarm> = emptyList(),
    val ringtoneList: List<Pair<String, Uri>> = emptyList(),
    val createAlarm: SnoozelooAlarm = SnoozelooAlarm(),
    val errorMessage: String? = null
)