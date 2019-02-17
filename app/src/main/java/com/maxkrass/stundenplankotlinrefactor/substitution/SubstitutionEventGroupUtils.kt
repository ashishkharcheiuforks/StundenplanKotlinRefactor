package com.maxkrass.stundenplankotlinrefactor.substitution

import android.content.Context
import android.preference.PreferenceManager
import com.maxkrass.stundenplankotlinrefactor.data.Grade
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEventGroup
import com.maxkrass.stundenplankotlinrefactor.settings.EF
import com.maxkrass.stundenplankotlinrefactor.settings.MY_SUBJECTS
import com.maxkrass.stundenplankotlinrefactor.settings.Q1
import com.maxkrass.stundenplankotlinrefactor.settings.Q2

object SubstitutionEventGroupUtils {

    fun convertEventListToGroupList(
            context: Context,
            events: List<SubstitutionEvent>,
            savedSubjects: Map<String, String>
    ): List<SubstitutionEventGroup> {

        val mySubjects = SubstitutionEventGroup("Meine Fächer")
        val efSubs = SubstitutionEventGroup("EF")
        val q1Subs = SubstitutionEventGroup("Q1")
        val q2Subs = SubstitutionEventGroup("Q2")

        events.forEach { event ->
            if (savedSubjects.isNotEmpty() && savedSubjects[event.subject] == event.gradeText) {
                mySubjects.events.add(event)
            } else {
                when (event.grade) {
                    Grade.EF -> efSubs.events.add(event)
                    Grade.Q1 -> q1Subs.events.add(event)
                    Grade.Q2 -> q2Subs.events.add(event)
                    Grade.LR -> TODO()
                    Grade.SL -> TODO()
                    Grade.None -> TODO()
                }
            }
        }

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)

        return mutableListOf<SubstitutionEventGroup>().apply {
            if (preferences.getBoolean(MY_SUBJECTS, true) && mySubjects.events.isNotEmpty())
                add(mySubjects)
            if (preferences.getBoolean(EF, true) && efSubs.events.isNotEmpty())
                add(efSubs)
            if (preferences.getBoolean(Q1, true) && q1Subs.events.isNotEmpty())
                add(q1Subs)
            if (preferences.getBoolean(Q2, true) && q2Subs.events.isNotEmpty())
                add(q2Subs)
            if (isEmpty())
                add(SubstitutionEventGroup("keine Vertretungen"))
        }.toList()
    }

    fun convertEventList(
            events: List<SubstitutionEvent>,
            savedSubjects: Map<String, String>): Set<SubstitutionEventGroup> {

        val mySubjects = SubstitutionEventGroup("Meine Fächer")

        return events
                .groupBy { it.grade }
                .map { entry ->
                    SubstitutionEventGroup(entry.key.toString(), entry.value.toMutableList())
                }.apply {
                    forEach { eventGroup ->
                        eventGroup.events.forEach { event ->
                            if (savedSubjects.isNotEmpty() && savedSubjects[event.subject] == event.gradeText) {
                                mySubjects.events.add(event)
                                eventGroup.events.remove(event)
                            }
                        }
                    }
                }
                .toMutableSet()
                .apply {
                    add(mySubjects)
                }

    }
}