package com.example.snoozeloo.data

data class AppUiState(
    val isLoading: Boolean = false,
    val items: List<SnoozelooAlarm> = emptyList(),
    val errorMessage: String? = null
)