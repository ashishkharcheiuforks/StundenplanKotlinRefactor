package com.maxkrass.stundenplankotlinrefactor.stundenplan

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.core.view.get
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.data.Lesson
import com.maxkrass.stundenplankotlinrefactor.data.Lessons
import com.maxkrass.stundenplankotlinrefactor.data.Weekday
import com.maxkrass.stundenplankotlinrefactor.extensions.asDp
import com.maxkrass.stundenplankotlinrefactor.extensions.get
import com.maxkrass.stundenplankotlinrefactor.extensions.visibility
import org.jetbrains.anko.find

/**
 * Max made this for StundenplanKotlinRefactor on 18.07.2017.
 */

class StundenplanViewScaler(private val stundenplanAdapter: StundenplanAdapter,
                            private val lessons: Map<Weekday, Lessons>,
                            private val containers: Array<RelativeLayout>) {

    private var offset = 0

    fun doScale() {
        for (weekdayIndex in 0 until Weekday.values().size) {

            val weekday: Weekday = Weekday.values()[weekdayIndex]
            val weekdayLessons: Lessons? = lessons[weekday]
            val weekdayContainer = containers[weekday]
            if (weekdayLessons != null) {
                for (i in 0 until weekdayLessons.size()) {
                    val lesson: Lesson? = weekdayLessons.valueAt(i)
                    lesson?.let {
                        scaleView(lesson, weekday, weekdayContainer, i)
                    }
                }
            }

            weekdayContainer.requestLayout()
        }
    }

    private fun scaleView(lesson: Lesson, weekday: Weekday, weekdayContainer: ViewGroup, index: Int) {
        if (stundenplanAdapter.isSucceedingLesson(weekday, lesson)) {
            offset++
        } else {
            val lessonCard = weekdayContainer[index - offset] as CardView

            lessonCard.find<View>(R.id.room_label).visibility(stundenplanAdapter.showRoomOnSingleLesson)

            val layoutParams: RelativeLayout.LayoutParams = lessonCard.layoutParams as RelativeLayout.LayoutParams

            val measures = stundenplanAdapter.originalMeasurements[weekday]?.get(lesson.period)

            layoutParams.topMargin = (measures?.first?.times(stundenplanAdapter.mScalingFactor))?.toInt()?.asDp() ?: 0

            layoutParams.height = (measures?.second?.times(stundenplanAdapter.mScalingFactor))?.toInt()?.asDp() ?: 0
        }
    }

}
