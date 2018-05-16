package com.maxkrass.stundenplankotlinrefactor.data

import org.junit.Assert
import org.junit.Test

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

        Assert.assertEquals(lesson.key, "mylessonkey")
        Assert.assertEquals(lesson.location, "0D4")
        Assert.assertEquals(lesson.period, 0)
        Assert.assertEquals(lesson.weekday, "monday")
        Assert.assertEquals(lesson.subject, "Englisch")
    }

}