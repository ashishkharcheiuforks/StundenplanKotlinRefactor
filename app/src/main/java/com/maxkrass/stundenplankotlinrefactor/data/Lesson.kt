package com.maxkrass.stundenplankotlinrefactor.data

import android.util.SparseArray
import java.io.Serializable
import java.security.InvalidParameterException

typealias Lessons = SparseArray<Lesson> // TODO("Consider reimplementing with kotlin.Collections")
typealias LessonLocation = String
typealias LessonKey = String
typealias LessonWeekday = String

data class Lesson(
    var subject: SubjectName,
    var weekday: LessonWeekday,
    var period: PeriodIndex,
    var location: LessonLocation,
    var key: LessonKey
) : Serializable, Comparable<Lesson>, FirebaseEntity {

    constructor() : this(
            subject = "No Lesson Subject",
            weekday = Weekday.MONDAY.toString(),
            period = 0,
            location = "No Location",
            key = "")

    init {
        if (subject.isEmpty()) throw InvalidParameterException("Subject Name mustn't be null")
    }

    override fun toMap(): Map<String, Any> = mapOf(
            "subject" to subject,
            "weekday" to weekday,
            "period" to period,
            "location" to location,
            "key" to key)

    override fun compareTo(other: Lesson): Int =
            if (weekday == other.weekday) {
                period.compareTo(other = other.period)
            } else {
                weekday.compareTo(other = other.weekday)
            }
}
