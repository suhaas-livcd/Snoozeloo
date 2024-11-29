package com.example.snoozeloo

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.core.content.getSystemService
import com.example.snoozeloo.data.AlarmMetaInfo
import kotlin.time.Duration.Companion.milliseconds

object AppUtils {
    private const val AM = "AM"
    private const val PM = "PM"

    fun getAlarmMetaInfo(timeHours: Int, timeMinutes: Int, repeatDays: Int): AlarmMetaInfo {
        val amPM = getAmPmText(timeHours, timeMinutes)
        val nextAlarmInfo =
            getNextAlarmTimeRemaining(timeHours, timeMinutes, repeatDays, amPM)
        return AlarmMetaInfo(
            alarmAmPm = amPM,
            nextAlarmTimeRemaining = nextAlarmInfo.first,
            sleepBedTime = nextAlarmInfo.second
        )
    }

    private fun getAmPmText(timeHours: Int, timeMinutes: Int): String {
        return if (timeHours < 12 || (timeHours == 12 && timeMinutes == 0)) {
            AM
        } else {
            PM
        }
    }

    private fun getNextAlarmTimeRemaining(
        timeHours: Int,
        timeMinutes: Int,
        repeatDays: Int,
        amPM: String
    ): Pair<String, String?> {
        val now = Calendar.getInstance()
        val nextAlarm = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, timeHours)
            set(Calendar.MINUTE, timeMinutes)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // If the alarm time is in the past, add the repeat days
            if (timeInMillis < now.timeInMillis) {
                add(Calendar.DAY_OF_YEAR, repeatDays)
            }
        }

        val diff = nextAlarm.timeInMillis - now.timeInMillis
        val days = diff.milliseconds.inWholeDays
        val hours = diff.milliseconds.inWholeHours % 24
        val minutes = diff.milliseconds.inWholeMinutes % 60

        val nextAlarmTimeRemaining =
            "Alarm in ${if (days in 0..6) "${days}d " else ""}${if (hours > 0) "${hours}h " else ""}${minutes}min"
        var sleepBedTimeTip: String? = null

        if (amPM == AM && timeHours in 4..10) {
            nextAlarm.apply { add(Calendar.HOUR_OF_DAY, -8) }
            val bedtimeHours = nextAlarm.get(Calendar.HOUR_OF_DAY)
            val bedtimeMinutes = nextAlarm.get(Calendar.MINUTE)
            val amPm =
                if (bedtimeHours < 12 || (bedtimeHours == 12 && bedtimeMinutes == 0)) AM else PM
            val formattedHours =
                if (bedtimeHours == 0) 12 else bedtimeHours % 12 // Handle 12-hour format
            val formattedMinutes =
                String.format("%02d", bedtimeMinutes) // Format minutes with leading zero

            sleepBedTimeTip =
                "Go to bed at $formattedHours:$formattedMinutes $amPm to get 8h of sleep"
        }

        return Pair(nextAlarmTimeRemaining, sleepBedTimeTip)
    }
}
