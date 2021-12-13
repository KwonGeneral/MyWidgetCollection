package com.kwon.mywidgetcollection.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateUtil {
    fun getDefaultDateTime(): Date {
        val it = Calendar.getInstance(Locale.KOREAN)
        it.set(Calendar.YEAR, 0)
        it.set(Calendar.MONTH, 0)
        it.set(Calendar.DATE, 0)
        it.set(Calendar.HOUR, 0)
        it.set(Calendar.MINUTE, 0)
        it.set(Calendar.SECOND, 0)
        return it.time
    }

    fun getLocalDateTime(): Date {
        return Calendar.getInstance(Locale.KOREAN).time
    }
}