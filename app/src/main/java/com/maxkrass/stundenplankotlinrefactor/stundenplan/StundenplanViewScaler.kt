package com.maxkrass.stundenplankotlinrefactor.stundenplan

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.core.util.valueIterator
import androidx.core.view.get
import androidx.core.view.isVisible
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.data.Lesson
import com.maxkrass.stundenplankotlinrefactor.data.Lessons
import com.maxkrass.stundenplankotlinrefactor.data.Weekday
import com.maxkrass.stundenplankotlinrefactor.extensions.asDp
import com.maxkrass.stundenplankotlinrefactor.extensions.get
import org.jetbrains.anko.find

/**
 * Max made this for StundenplanKotlinRefactor on 18.07.2017.
 */

class StundenplanViewScaler(
    private val stundenplanAdapter: StundenplanAdapter,
    private val lessons: Map<Weekday, Lessons>,
    private val containers: Array<RelativeLayout>
) {

    private var offset = 0

    fun doScale() {
        for (weekday in Weekday.values()) {
            val weekdayLessons = lessons[weekday]!!
            val weekdayContainer = containers[weekday]
            for (lesson in weekdayLessons.valueIterator()) {
                scaleView(lesson, weekday, weekdayContainer, weekdayLessons.indexOfValue(lesson))
            }

            weekdayContainer.requestLayout()
        }
    }

    private fun scaleView(lesson: Lesson, weekday: Weekday, weekdayContainer: ViewGroup, index: Int) {
        if (stundenplanAdapter.isSucceedingLesson(weekday, lesson)) {
            offset++
        } else {
            val lessonCard = weekdayContainer[index - offset] as CardView

            lessonCard.find<View>(R.id.room_label).isVisible = stundenplanAdapter.showRoomOnSingleLesson

            val layoutParams: RelativeLayout.LayoutParams = lessonCard.layoutParams as RelativeLayout.LayoutParams

            val measures = stundenplanAdapter.originalMeasurements[weekday]?.get(lesson.period)

            layoutParams.topMargin = (measures?.first?.times(stundenplanAdapter.mScalingFactor))?.toInt()?.asDp() ?: 0

            layoutParams.height = (measures?.second?.times(stundenplanAdapter.mScalingFactor))?.toInt()?.asDp() ?: 0
        }
    }
}
