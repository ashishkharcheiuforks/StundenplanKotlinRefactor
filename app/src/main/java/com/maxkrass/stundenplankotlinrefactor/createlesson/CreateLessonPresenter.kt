package com.maxkrass.stundenplankotlinrefactor.createlesson

import com.maxkrass.stundenplankotlinrefactor.commons.CreateLessonView
import com.maxkrass.stundenplankotlinrefactor.data.LessonWeekday
import com.maxkrass.stundenplankotlinrefactor.data.PeriodIndex
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.utils.Validator
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView

class CreateLessonPresenter<V : CreateLessonView> : TiPresenter<V>() {

    var subject: Subject? = null

    override fun onAttachView(view: V) {
        super.onAttachView(view)
        subject?.let {
            view.onSubjectChosen(it)
        }
    }

    fun validateLesson(weekday: LessonWeekday, periodIndex: PeriodIndex, doublePeriod: Boolean, location: Validator) {
        deliverToView { removeErrors() }
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    fun onSubjectChosen(subject: Subject?) {
        this.subject = subject
        deliverToView {
            if (subject == null) {
                onNoSubjectChosen()
            } else {
                onSubjectChosen(subject)
            }
        }
    }
}