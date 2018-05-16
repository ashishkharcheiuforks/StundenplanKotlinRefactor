package com.maxkrass.stundenplankotlinrefactor.extensions

import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Max made this for StundenplanKotlinRefactor on 06.12.2017.
 */

operator fun Calendar.minus(time: Calendar): Long =
        TimeUnit.MILLISECONDS.toMinutes(timeInMillis - time.timeInMillis)

object CalendarUtil {
    fun create(hour: Int, minute: Int): Calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
    }
}