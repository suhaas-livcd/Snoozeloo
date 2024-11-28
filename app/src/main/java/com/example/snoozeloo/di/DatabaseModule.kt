package com.example.snoozeloo.di

import android.content.Context
import androidx.room.Room
import com.example.snoozeloo.data.SnoozelooAlarmDao
import com.example.snoozeloo.data.SnoozelooAlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SnoozelooAlarmDatabase {
        return Room.databaseBuilder(
            context, SnoozelooAlarmDatabase::class.java, "snoozeloo_alarms"
        ).build()
    }

    @Provides
    fun provideMyDao(database: SnoozelooAlarmDatabase): SnoozelooAlarmDao {
        return database.snoozelooAlarmDao
    }
}