package com.maxkrass.stundenplankotlinrefactor.substitution

import com.maxkrass.stundenplankotlinrefactor.data.Grade
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView

/**
 * Max made this for Stundenplan2 on 02.05.2017.
 */

class SingleDaySubstitutionPresenter(uid: String, index: Int) :
        TiPresenter<SingleDaySubstitutionContract.View>(),
        SingleDaySubstitutionContract.Presenter {

    override fun onSubstitutionDayLoaded(day: String) {
        deliverToView { setTabTitle(day) }
    }

    override fun onConnectionError() {
        deliverToView { showConnectionError() }
    }

    override fun onSubstitutionEventsLoaded(events: List<SubstitutionEvent>) {
        mLastEvents.clear()
        mLastEvents.addAll(events)

        deliverToView { updateList(mLastEvents) }
    }

    private val mSubstitutionModel = SingleDaySubstitutionRepository(this, uid, index).apply {
        loadSubstitutionSubjects()
    }
    private val mSubstitutionSubjects: MutableMap<String, String> = mutableMapOf()
    private val substitutionSubjects = mutableMapOf<String, Grade>()
    private val mLastEvents: MutableList<SubstitutionEvent> = mutableListOf()

    override fun onSubstitutionSubjectsLoaded(substitutionSubjects: Map<String, String>) {
        with(mSubstitutionSubjects) {
            clear()
            putAll(substitutionSubjects)
        }
    }

    fun addSubstitutionSubjectClicked() {
        deliverToView {
            if (selectedEvent.item.subject.isNotBlank() && selectedEvent.item.grade != Grade.None) {
                substitutionSubjects[selectedEvent.item.subject] = selectedEvent.item.grade
                mSubstitutionModel.updateSubstitutionSubjects(substitutionSubjects) {
                    updateList(mLastEvents)
                }
            }
        }
    }

    override fun onAttachView(view: SingleDaySubstitutionContract.View) {
        super.onAttachView(view)
        refreshItems()
    }

    fun refreshItems() {
        deliverToView { showLoading() }

        mSubstitutionModel.loadSubstitutions()
    }
}
