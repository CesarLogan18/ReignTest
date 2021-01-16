package com.reign.test

import java.util.*

object Utils {

    fun getCurrentTimeDiffInSeconds(timeInSeconds: Long): Long {
        val currentDate: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val currentSeconds = currentDate.timeInMillis / 1000
        return currentSeconds - timeInSeconds
    }
}