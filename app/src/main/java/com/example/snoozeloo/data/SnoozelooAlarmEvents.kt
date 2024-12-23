package com.example.snoozeloo.data

import android.net.Uri

sealed interface SnoozelooAlarmEvents {
    // Alarm List Screen

    data object CreateAlarm : SnoozelooAlarmEvents

    // Alarm Details Screen - Enable/Disable checkbox, Create alarm, Cancel, Save
    data class SetAlarmIsEnabled(val alarmId: Int, val isEnabled: Boolean) : SnoozelooAlarmEvents
    data class SetAlarmTimeHours(val hours: Int) : SnoozelooAlarmEvents
    data class SetAlarmTimeMinutes(val minutes: Int) : SnoozelooAlarmEvents
    class SetAlarmVolume(val alarmVolume: Float) : SnoozelooAlarmEvents
    class SetVibrateIsEnabled(val vibrateEnabled: Boolean) : SnoozelooAlarmEvents
    class SetRepeatDay(val dayText: Int, val isDaySelected: Boolean) : SnoozelooAlarmEvents
    data object CancelAlarm : SnoozelooAlarmEvents
    data object SaveAlarm : SnoozelooAlarmEvents

    // Alarm Name Dialog : Save
    data object ShowAlarmNameDialog : SnoozelooAlarmEvents
    data class SetAlarmNameDialogText(val text: String) : SnoozelooAlarmEvents
    data object SaveAlarmNameDialog : SnoozelooAlarmEvents

    // Alarm Ringtone : Select, Back button
    data object ClickOnAlarmRingtone : SnoozelooAlarmEvents
    data class SelectAlarmRingtone(val ringtoneInfo: Pair<String, Uri>) : SnoozelooAlarmEvents
    data object CancelAlarmRingtone : SnoozelooAlarmEvents

    // Alarm Trigger : TurnOff, Snooze
    data class ShowAlarmTrigger(val alarm: SnoozelooAlarm) : SnoozelooAlarmEvents

    data object TurnOffAlarm : SnoozelooAlarmEvents
    data object SnoozeAlarm : SnoozelooAlarmEvents

}