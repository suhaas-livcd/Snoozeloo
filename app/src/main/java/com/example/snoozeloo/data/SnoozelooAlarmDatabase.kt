package com.example.snoozeloo.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SnoozelooAlarm::class],
    version = 1,

    )
abstract class SnoozelooAlarmDatabase : RoomDatabase() {
    abstract val snoozelooAlarmDao: SnoozelooAlarmDao
}