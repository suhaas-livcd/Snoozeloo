package com.example.snoozeloo.di

import android.content.Context
import android.media.RingtoneManager
import com.example.snoozeloo.AlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {
    @Provides
    @Singleton
    fun provideAlarmScheduler(@ApplicationContext context: Context): AlarmScheduler {
        return AlarmScheduler(context)
    }

    @Provides
    @Singleton
    fun provideRingtoneManager(@ApplicationContext context: Context): RingtoneManager {
        return RingtoneManager(context)
    }
}