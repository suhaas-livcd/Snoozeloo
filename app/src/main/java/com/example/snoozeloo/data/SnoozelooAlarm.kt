package com.example.snoozeloo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SnoozelooAlarm(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "Alarm",
    val timeHours: Int = 0,
    val timeMinutes: Int = 0,
    val isEnabled: Boolean = true,
    val repeatDays: Int = 0,
    val alarmRingtoneURI: String = "Silent",
    val alarmRingtoneTitle: String = "Default",
    val isAlarmSilent: Boolean = false,
    val alarmVolume: Float = 0.5f,
    val vibrate: Boolean = true
)

