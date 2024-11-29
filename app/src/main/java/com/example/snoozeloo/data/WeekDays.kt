package com.example.snoozeloo.data

object WeekDays {
    const val MONDAY = 1 shl 0
    const val TUESDAY = 1 shl 1
    const val WEDNESDAY = 1 shl 2
    const val THURSDAY = 1 shl 3
    const val FRIDAY = 1 shl 4
    const val SATURDAY = 1 shl 5
    const val SUNDAY = 1 shl 6

    fun isDaySelected(bitmask: Int, day: Int): Boolean = (bitmask and day) != 0

    fun selectDay(bitmask: Int, day: Int): Int = bitmask or day

    fun deselectDay(bitmask: Int, day: Int): Int = bitmask and day.inv()
}