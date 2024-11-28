package com.example.snoozeloo.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SnoozelooAlarmDao {
    @Query("SELECT * FROM snoozelooalarm")
    fun getAllAlarms(): Flow<List<SnoozelooAlarm>>

    @Upsert
    fun upsertAlarm(alarm: SnoozelooAlarm)

    @Query("DELETE FROM snoozelooalarm WHERE id = :id")
    fun deleteAlarmById(id: Int)

    @Query("SELECT * FROM snoozelooalarm ORDER BY time ASC")
    fun orderAlarmsByTime(): Flow<List<SnoozelooAlarm>>
}