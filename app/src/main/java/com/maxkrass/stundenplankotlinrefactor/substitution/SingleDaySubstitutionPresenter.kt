package com.maxkrass.stundenplankotlinrefactor.substitution

import com.google.firebase.database.FirebaseDatabase
import com.maxkrass.stundenplankotlinrefactor.data.Grade
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import com.maxkrass.stundenplankotlinrefactor.extensions.addSingleEventListener
import com.maxkrass.stundenplankotlinrefactor.extensions.getTypedValue
import net.grandcentrix.thirtyinch.TiPresenter

/**
 * Max made this for Stundenplan2 on 02.05.2017.
 */

class SingleDaySubstitutionPresenter(
        uid: String,
        index: Int) :
        TiPresenter<SingleDaySubstitutionContract.View>(),
        SingleDaySubstitutionContract.Presenter {

    private val mSubstitutionModel = SingleDaySubstitutionRepository(this, uid).apply {
        loadSubstitutionSubjects()
    }
    private var mSubstitutionSubjects: Map<String, String>? = null
    private val substitutionSubjects = mutableMapOf<String, Grade>()
    private val mLastEvents: MutableList<SubstitutionEvent> = mutableListOf()
    private val mSubstitutionDayRef = FirebaseDatabase
            .getInstance()
            .reference
            .child("stundenplan")
            .child("latestSubstitutionPlans")
            .child("day$index")

    override fun onSubstitutionSubjectsLoaded(substitutionSubjects: Map<String, String>) {
        mSubstitutionSubjects = substitutionSubjects
    }

    fun addSubstitutionSubjectClicked() {
        view?.let { view ->
            if (view.selectedEvent.item.subject.isNotBlank() && view.selectedEvent.item.grade != Grade.None) {
                substitutionSubjects[view.selectedEvent.item.subject] = view.selectedEvent.item.grade
                mSubstitutionModel.updateSubstitutionSubjects(substitutionSubjects) {
                    view.updateList(mLastEvents)
                }
            }
        }


    }

    override fun onAttachView(view: SingleDaySubstitutionContract.View) {
        super.onAttachView(view)
        refreshItems()
    }

    fun refreshItems() {
        view?.showLoading()
        mSubstitutionDayRef.child("plan").addSingleEventListener {
            onDataChange { snapshot ->
                val events: List<@JvmSuppressWildcards SubstitutionEvent>? = snapshot.getTypedValue()
                mLastEvents.clear()
                if (events?.isNotEmpty() == true) {
                    mLastEvents.addAll(events)
                }
                view?.updateList(mLastEvents)
            }

            onCancelled {
                view?.showConnectionError()
            }
        }

        mSubstitutionDayRef.child("correspondingDate").addSingleEventListener {
            onDataChange { snapshot ->
                val dateDay = snapshot.getValue(String::class.java)
                if (dateDay?.isNotBlank() == true) {
                    val day = dateDay
                            .split(" ")
                            .dropLastWhile { it.isEmpty() }
                            .toTypedArray()[1]
                    if (day.isNotBlank()) {
                        view?.setTabTitle(day)
                    }
                }
            }
        }
    }
}
