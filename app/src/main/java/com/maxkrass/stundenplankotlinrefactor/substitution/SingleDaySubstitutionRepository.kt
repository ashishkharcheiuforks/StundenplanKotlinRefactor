package com.maxkrass.stundenplankotlinrefactor.substitution

import com.crashlytics.android.Crashlytics
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.maxkrass.stundenplankotlinrefactor.data.Grade
import com.maxkrass.stundenplankotlinrefactor.extensions.addSingleEventListener
import com.maxkrass.stundenplankotlinrefactor.extensions.addValueEventListener
import com.maxkrass.stundenplankotlinrefactor.extensions.getTypedValue
import com.maxkrass.stundenplankotlinrefactor.extensions.getValueAs

/**
 * Max made this for Stundenplan2 on 02.05.2017.
 */

class SingleDaySubstitutionRepository(
    private val mPresenter: SingleDaySubstitutionContract.Presenter,
    uid: String,
    index: Int
) {
    private val mSubstitutionSubjectsRef: DatabaseReference = FirebaseDatabase
            .getInstance()
            .reference
            .child("users")
            .child(uid)
            .child("substitutionSubjects")
    private val mSubstitutionDayRef = FirebaseDatabase
            .getInstance()
            .reference
            .child("stundenplan")
            .child("latestSubstitutionPlans")
            .child("day$index")

    fun loadSubstitutionSubjects() {
        mSubstitutionSubjectsRef.addValueEventListener {
            onDataChange { dataSnapshot ->
                if (dataSnapshot.hasChildren()) {
                    mPresenter.onSubstitutionSubjectsLoaded(dataSnapshot.getValueAs<HashMap<String, String>>() ?: mapOf())
                }
            }
            onCancelled {
                Crashlytics.logException(it.toException())
            }
        }
    }

    fun updateSubstitutionSubjects(
        substitutionSubjects: Map<String, Grade>,
        onSuccess: (Void) -> Unit
    ) {
        mSubstitutionSubjectsRef.setValue(substitutionSubjects).addOnSuccessListener(onSuccess)
    }

    fun loadSubstitutions() {
        mSubstitutionDayRef.child("plan").addSingleEventListener {
            onDataChange { snapshot ->
                mPresenter.onSubstitutionEventsLoaded(snapshot.getTypedValue() ?: listOf())
            }

            onCancelled {
                mPresenter.onConnectionError()
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
                        mPresenter.onSubstitutionDayLoaded(day)
                    }
                }
            }
        }
    }
}
