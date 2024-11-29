package com.example.snoozeloo.data

import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class AlarmsRepository @Inject constructor(private val snoozelooAlarmDao: SnoozelooAlarmDao) {
    init {
        Timber.d("Initializing AlarmsRepository")
    }


    fun fetchAlarms(): Flow<List<SnoozelooAlarm>> {
        return snoozelooAlarmDao.getAllAlarms()
    }

    fun upsertAlarm(alarm: SnoozelooAlarm) {
        Timber.d("Creating alarm : $alarm")
        snoozelooAlarmDao.upsertAlarm(alarm = alarm)
    }
}