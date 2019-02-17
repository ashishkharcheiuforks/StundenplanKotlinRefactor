package com.maxkrass.stundenplankotlinrefactor.substitution

import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import net.grandcentrix.thirtyinch.TiView

/**
 * Max made this for Stundenplan2 on 02.05.2017.
 */

object SingleDaySubstitutionContract {

    interface View : TiView {

        val selectedEvent: Binder<SubstitutionEvent>

        fun updateList(events: List<SubstitutionEvent>)

        fun showLoading()

        fun hideLoading()

        fun showConnectionError()

        fun setTabTitle(title: String)
    }

    interface Presenter {

        fun onSubstitutionSubjectsLoaded(substitutionSubjects: Map<String, String>)

        fun onSubstitutionEventsLoaded(events: List<SubstitutionEvent>)

        fun onConnectionError()
        fun onSubstitutionDayLoaded(day: String)
    }

}
