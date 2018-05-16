package com.maxkrass.stundenplankotlinrefactor.substitution

import com.google.firebase.crash.FirebaseCrash
import com.google.firebase.database.*
import com.maxkrass.stundenplankotlinrefactor.data.Grade
import com.maxkrass.stundenplankotlinrefactor.extensions.getValueAs

/**
 * Max made this for Stundenplan2 on 02.05.2017.
 */

class SingleDaySubstitutionRepository(private val mPresenter: SingleDaySubstitutionContract.Presenter,
                                      uid: String) {
    private val mSubstitutionSubjectsRef: DatabaseReference = FirebaseDatabase
            .getInstance()
            .reference
            .child("users")
            .child(uid)
            .child("substitutionSubjects")

    fun loadSubstitutionSubjects() {
        mSubstitutionSubjectsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    mPresenter.onSubstitutionSubjectsLoaded(dataSnapshot.getValueAs<HashMap<String, String>>() ?: mapOf())
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                FirebaseCrash.report(databaseError.toException())
            }
        })
    }

    fun updateSubstitutionSubjects(substitutionSubjects: Map<String, Grade>,
                                   onSuccess: (Void) -> Unit) {
        mSubstitutionSubjectsRef.setValue(substitutionSubjects).addOnSuccessListener(onSuccess)
    }
}
