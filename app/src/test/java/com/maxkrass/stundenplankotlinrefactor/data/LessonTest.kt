package com.maxkrass.stundenplankotlinrefactor.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Max made this for StundenplanKotlinRefactor on 08.09.2017.
 */
class LessonTest {

    @Test
    @Throws(Exception::class)
    fun checkLesson() {

        val lesson = Lesson(
                subject = "Englisch",
                weekday = Weekday.MONDAY.toString(),
                period = 0,
                location = "0D4",
                key = "mylessonkey"
        )

        assertEquals(lesson.key, "mylessonkey")
        assertEquals(lesson.location, "0D4")
        assertEquals(lesson.period, 0)
        assertEquals(lesson.weekday, "monday")
        assertEquals(lesson.subject, "Englisch")
    }
}
