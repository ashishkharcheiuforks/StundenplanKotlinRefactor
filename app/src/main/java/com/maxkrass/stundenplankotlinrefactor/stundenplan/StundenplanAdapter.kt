package com.maxkrass.stundenplankotlinrefactor.stundenplan

import android.util.SparseArray
import android.widget.RelativeLayout
import androidx.core.util.forEach
import com.maxkrass.stundenplankotlinrefactor.data.*
import java.util.*

/**
 * Max made this for StundenplanKotlinRefactor on 27.05.2017.
 */

class StundenplanAdapter(val containers: Array<RelativeLayout>) {

    internal val lessons: Map<Weekday, Lessons> = mutableMapOf(
            Weekday.MONDAY to SparseArray(),
            Weekday.TUESDAY to SparseArray(),
            Weekday.WEDNESDAY to SparseArray(),
            Weekday.THURSDAY to SparseArray(),
            Weekday.FRIDAY to SparseArray())

    internal val periods: List<Period> = listOf(
            Period(index = 0, startHour = 8, startMinute = 10, endHour = 8, endMinute = 55),
            Period(index = 1, startHour = 9, startMinute = 0, endHour = 9, endMinute = 45),
            Period(index = 2, startHour = 9, startMinute = 50, endHour = 10, endMinute = 35),
            Period(index = 3, startHour = 11, startMinute = 5, endHour = 11, endMinute = 50),
            Period(index = 4, startHour = 11, startMinute = 55, endHour = 12, endMinute = 40),
            Period(index = 5, startHour = 12, startMinute = 45, endHour = 13, endMinute = 30),
            Period(index = 6, startHour = 14, startMinute = 15, endHour = 15, endMinute = 0),
            Period(index = 7, startHour = 15, startMinute = 0, endHour = 15, endMinute = 45),
            Period(index = 8, startHour = 15, startMinute = 55, endHour = 16, endMinute = 40),
            Period(index = 9, startHour = 16, startMinute = 40, endHour = 17, endMinute = 25)
    )

    private val firstPeriodStartTime: Calendar = Calendar.getInstance()
    internal var mScalingFactor: Float = 1.0f
    internal var showRoomOnSingleLesson: Boolean = true
    internal val originalMeasurements: Map<Weekday, SparseArray<Pair<Long, Long>>> = mutableMapOf(
            Weekday.MONDAY to SparseArray(),
            Weekday.TUESDAY to SparseArray(),
            Weekday.WEDNESDAY to SparseArray(),
            Weekday.THURSDAY to SparseArray(),
            Weekday.FRIDAY to SparseArray())

    init {

        firstPeriodStartTime.set(Calendar.HOUR_OF_DAY, periods[0].startHour)
        firstPeriodStartTime.set(Calendar.MINUTE, periods[0].startMinute)
    }

    fun addLessons(lessons: Lessons, subjects: Subjects) {
        lessons.forEach { _, lesson -> addLesson(lesson, subjects[lesson.subject]) }
    }

    private fun addLesson(lesson: Lesson, subject: Subject? = null) {
        StundenplanLessonAdder.addLesson(this, lesson, subject)
    }

    internal fun scaleViews() {
        StundenplanViewScaler(this, lessons, containers).doScale()
    }

    internal fun isSucceedingLesson(weekday: Weekday, lesson: Lesson): Boolean {
        val potentialLesson: Lesson? = lessons[weekday]?.get(lesson.period - 1)
        return potentialLesson != null &&
                Objects.equals(lesson.period, potentialLesson.period + 1) &&
                lesson.subject == potentialLesson.subject
    }

    internal fun hasSucceedingLesson(weekday: Weekday, lesson: Lesson): Boolean {
        val potentialLesson: Lesson? = lessons[weekday]?.get(lesson.period + 1)
        return potentialLesson != null &&
                Objects.equals(lesson.period + 1, potentialLesson.period) &&
                Objects.equals(lesson.subject, potentialLesson.subject)
    }
}
