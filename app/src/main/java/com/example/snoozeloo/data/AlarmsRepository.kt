package com.example.snoozeloo.data

import timber.log.Timber
import javax.inject.Inject

class AlarmsRepository @Inject constructor() {
    init {
        Timber.d("Initializing AlarmsRepository")
    }
}