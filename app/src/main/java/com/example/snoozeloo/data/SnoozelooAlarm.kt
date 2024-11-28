package com.example.snoozeloo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SnoozelooAlarm(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val time: String,
    val isEnabled: Boolean,
    val repeatDays: Int,
    val sound: String = "Default"
)