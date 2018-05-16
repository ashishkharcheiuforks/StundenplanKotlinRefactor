package com.maxkrass.stundenplankotlinrefactor.stundenplan

import android.graphics.Color
import android.view.Gravity
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.data.*
import com.maxkrass.stundenplankotlinrefactor.extensions.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class StundenplanLessonUI(private val stundenplanAdapter: StundenplanAdapter,
                          private val lesson: Lesson,
                          private val subject: Subject?,
                          private val weekday: Weekday,
                          private val periods: List<Period>) : AnkoComponent<RelativeLayout> {

    private val firstPeriodStartTime: Calendar by lazy {
        CalendarUtil.create(periods[0].startHour,
                            periods[0].startMinute)
    }

    private val periodEndTime by lazy {
        val latestPossibleConsecutivePeriod = periods[lesson.period + stundenplanAdapter.hasSucceedingLesson(
                weekday,
                lesson).toInt()]
        CalendarUtil.create(
                latestPossibleConsecutivePeriod.endHour,
                latestPossibleConsecutivePeriod.endMinute)
    }

    private val periodStartTime by lazy {
        CalendarUtil.create(periods[lesson.period].startHour,
                            periods[lesson.period].startMinute)
    }

    private val startDifference by lazy { periodStartTime - firstPeriodStartTime }

    private val periodLength by lazy { periodEndTime - periodStartTime }

    override fun createView(ui: AnkoContext<RelativeLayout>): CardView = with(ui) {

        themedCardView(R.style.Selectable) {

            setCardBackgroundColor(subject?.colorInt ?: 0)

            verticalLayout {

                gravity = Gravity.CENTER

                themedTextView(R.style.NotSelectable) {
                    id = R.id.subject_abbr_label
                    text = subject?.abbreviation
                    textAppearanceResource = R.style.TextAppearance_AppCompat_Medium
                    textColor = if (colorTooLight(subject?.color)) 0xDE000000.toInt() else 0xFFFFFFFF.toInt()
                }.lparams(width = wrapContent)

                themedTextView(R.style.NotSelectable) {
                    id = R.id.room_label
                    text = lesson.location
                    visibility(stundenplanAdapter.showRoomOnSingleLesson)
                    textColor = if (colorTooLight(subject?.color)) 0x8A000000.toInt() else 0xB3FFFFFF.toInt()
                }.lparams(width = wrapContent)

            }

            onClick {
                StundenplanLessonViewClick(
                        stundenplanAdapter,
                        lesson,
                        subject,
                        weekday)
            }

            layoutParams = RecyclerView.LayoutParams(matchParent,
                                                     dip((periodLength * stundenplanAdapter.mScalingFactor)))
                    .apply {
                        setMargins(
                                dip(2),
                                dip((startDifference * stundenplanAdapter.mScalingFactor)),
                                dip(2),
                                0)
                    }

            stundenplanAdapter
                    .originalMeasurements[weekday]
                    ?.put(lesson.period, startDifference to periodLength)

        }

    }

    private fun colorTooLight(color: SubjectColor?): Boolean {
        val hsvValues = FloatArray(3)
        Color.colorToHSV(Color.parseColor(color.toString()),
                         hsvValues)
        return hsvValues[2] > 0.99
    }
}
