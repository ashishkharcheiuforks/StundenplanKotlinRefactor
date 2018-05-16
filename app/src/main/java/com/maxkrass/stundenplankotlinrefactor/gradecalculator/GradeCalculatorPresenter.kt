package com.maxkrass.stundenplankotlinrefactor.gradecalculator

import com.google.firebase.database.FirebaseDatabase
import com.maxkrass.stundenplankotlinrefactor.data.Subjects
import com.maxkrass.stundenplankotlinrefactor.data.Uid
import com.maxkrass.stundenplankotlinrefactor.extensions.addValueEventListener
import com.maxkrass.stundenplankotlinrefactor.extensions.getTypedValue
import net.grandcentrix.thirtyinch.TiPresenter

/**
 * Max made this for StundenplanKotlinRefactor on 10.12.2017.
 */

class GradeCalculatorPresenter(private val uid: Uid) : TiPresenter<GradeCalculatorContract.View>() {

    override fun onAttachView(view: GradeCalculatorContract.View) {
        super.onAttachView(view)
        FirebaseDatabase
                .getInstance()
                .reference
                .child("subjects")
                .child(uid)
                .addValueEventListener {
                    onDataChange { dataSnapshot ->
                        view.subjectsLoaded(dataSnapshot.getTypedValue() ?: Subjects())
                    }
                }
    }

}