package com.maxkrass.stundenplankotlinrefactor.data

typealias PeriodIndex = Int

data class Period(
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int,
    val index: PeriodIndex
)
