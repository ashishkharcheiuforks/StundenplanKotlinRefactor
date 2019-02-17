package com.maxkrass.stundenplankotlinrefactor.stundenplan

import com.maxkrass.stundenplankotlinrefactor.commons.IStundenplanInteractor
import com.maxkrass.stundenplankotlinrefactor.commons.IStundenplanView
import com.maxkrass.stundenplankotlinrefactor.data.Lessons
import com.maxkrass.stundenplankotlinrefactor.data.Subjects
import com.maxkrass.stundenplankotlinrefactor.data.Weekday
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class StundenplanPresenter : TiPresenter<IStundenplanView>(),
        IStundenplanInteractor.OnLessonsLoadedListener, AnkoLogger {
    override fun onLessonsLoaded(lessons: Lessons, subjects: Subjects) {
        info("lessons loaded")
        deliverToView { updateWeekdayColumn(lessons, subjects) }
    }

    override fun onLoadingError() {
        deliverToView { showLoadingError() }
    }

    private val interactor: StundenplanInteractor by lazy { StundenplanInteractor() }

    override fun onCreate() {
        super.onCreate()
        info("onCreate")
    }

    override fun onAttachView(view: IStundenplanView) {
        super.onAttachView(view)
        info("Loading Lessons for Wednesday")
        interactor.loadLessons(Weekday.MONDAY, this)
        interactor.loadLessons(Weekday.TUESDAY, this)
        interactor.loadLessons(Weekday.WEDNESDAY, this)
        interactor.loadLessons(Weekday.THURSDAY, this)
        interactor.loadLessons(Weekday.FRIDAY, this)
    }
}
