package com.maxkrass.stundenplankotlinrefactor.stundenplan

import androidx.cardview.widget.CardView
import com.maxkrass.stundenplankotlinrefactor.data.Lesson
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Weekday
import com.maxkrass.stundenplankotlinrefactor.extensions.get
import org.jetbrains.anko.AnkoContext

/**
 * Max made this for StundenplanKotlinRefactor on 21.07.2017.
 */
object StundenplanLessonAdder {

    fun addLesson(stundenplanAdapter: StundenplanAdapter, lesson: Lesson, subject: Subject?) {

        val weekday = Weekday.fromString(lesson.weekday)

        stundenplanAdapter.lessons[weekday]?.put(lesson.period, lesson)

        val lessonView: CardView =
                StundenplanLessonUI(
                        stundenplanAdapter,
                        lesson,
                        subject,
                        weekday,
                        stundenplanAdapter.periods)
                        .createView(
                                stundenplanAdapter.containers[weekday].let {
                                    AnkoContext.create(it.context, it)
                                })

        stundenplanAdapter.containers[weekday].addView(lessonView)
    }
}
