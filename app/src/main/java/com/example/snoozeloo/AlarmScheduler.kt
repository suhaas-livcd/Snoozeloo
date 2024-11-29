package com.example.snoozeloo

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import com.example.snoozeloo.data.SnoozelooAlarm
import timber.log.Timber
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmScheduler(val context: Context) {
    init {
        Timber.d("AlarmScheduler initialized")
    }

    fun scheduleAlarm(alarm: SnoozelooAlarm) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AppReceiver::class.java).apply {
            val bundle = Bundle()
            bundle.putString("EXTRA_NAME", alarm.name)
            bundle.putInt("EXTRA_TIME_HOURS", alarm.timeHours)
            bundle.putInt("EXTRA_TIME_MINUTES", alarm.timeMinutes)
            bundle.putString("EXTRA_ALARM_RINGTONE_URI", alarm.alarmRingtoneURI)
            putExtras(bundle)
        }
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, alarm.timeHours)
            set(Calendar.MINUTE, alarm.timeMinutes)
            set(Calendar.SECOND, 0)
        }
        if (alarmManager.canScheduleExactAlarms()) {
            val currentTimeInMilliseconds = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
            if(calendar.timeInMillis > currentTimeInMilliseconds){
                Timber.d("Scheduling exact alarm : ${alarm.timeHours}, ${alarm.timeMinutes}, ${calendar.timeInMillis}, $calendar")
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    PendingIntent.getBroadcast(
                        context,
                        alarm.hashCode(),
                        alarmIntent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
            }
        }
    }
}