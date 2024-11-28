package com.example.snoozeloo.data

sealed interface SnoozelooAlarmEvents {
    // Alarm List Screen
    data object CreateAlarm : SnoozelooAlarmEvents

    // Alarm Details Screen - Enable/Disable checkbox, Create alarm, Cancel, Save
    data class SetAlarmIsEnabled(val isEnabled: Boolean) : SnoozelooAlarmEvents
    data object CancelAlarm : SnoozelooAlarmEvents
    data object SaveAlarm : SnoozelooAlarmEvents

    // Alarm Name Dialog : Save
    data object ShowAlarmNameDialog : SnoozelooAlarmEvents
    data class SetAlarmNameDialogText(val text: String) : SnoozelooAlarmEvents
    data object SaveAlarmNameDialog : SnoozelooAlarmEvents

    // Alarm Ringtone : Select, Back button
    data class SelectAlarmRingtone(val ringtone: String) : SnoozelooAlarmEvents
    data object CancelAlarmRingtone : SnoozelooAlarmEvents

    // Alarm Trigger : TurnOff, Snooze
    data class ShowAlarmTrigger(val alarm: SnoozelooAlarm) : SnoozelooAlarmEvents
    data object TurnOffAlarm : SnoozelooAlarmEvents
    data object SnoozeAlarm : SnoozelooAlarmEvents

}