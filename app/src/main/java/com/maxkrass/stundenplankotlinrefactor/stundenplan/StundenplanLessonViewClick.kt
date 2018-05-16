package com.maxkrass.stundenplankotlinrefactor.stundenplan

import android.view.View
import com.maxkrass.stundenplankotlinrefactor.createlesson.CreateLessonActivity
import com.maxkrass.stundenplankotlinrefactor.data.Lesson
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Weekday
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.startActivity

/**
 * Max made this for StundenplanKotlinRefactor on 18.07.2017.
 */

class StundenplanLessonViewClick(private val stundenplanAdapter: StundenplanAdapter,
                                 private val lesson: Lesson,
                                 private val subject: Subject?,
                                 private val weekday: Weekday) : View.OnClickListener {

    private val edit = "Edit"
    private val delete = "Delete"

    override fun onClick(lessonView: View) {
        with(lessonView.context) {
            alert(Appcompat, "", subject?.name) {
                items(listOf<CharSequence>(edit, delete)) { _, item, _ ->
                    when (item) {
                        edit   -> {
                            lessonView.context.startActivity<CreateLessonActivity>(
                                    "lesson" to lesson,
                                    "doublePeriod" to stundenplanAdapter.hasSucceedingLesson(
                                            weekday,
                                            lesson)
                            )
                        }
                        delete -> {
                        }
                    }
                }
            }.show()
        }
    }
}
