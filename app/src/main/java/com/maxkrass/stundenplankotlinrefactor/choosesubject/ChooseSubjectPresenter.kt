package com.maxkrass.stundenplankotlinrefactor.choosesubject

import com.maxkrass.stundenplankotlinrefactor.commons.ChooseSubjectView
import com.maxkrass.stundenplankotlinrefactor.createsubject.SubjectRepository
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Uid
import com.maxkrass.stundenplankotlinrefactor.managesubjects.FirestoreSubjectAdapter
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView

class ChooseSubjectPresenter(uid: Uid) :
        TiPresenter<ChooseSubjectView>(),
        FirestoreSubjectAdapter.SubjectViewHolder.Host {

    private val subjects = SubjectRepository(uid)

    override fun onSubjectClicked(subject: Subject) {
        deliverToView { onSubjectChosen(subject) }
    }

    override fun onSubjectLongClicked(subject: Subject): Boolean {
        var result = false
        deliverToView { result = onSubjectLongClicked(subject) }
        return result
    }

    override fun onCreate() {
        super.onCreate()
        subjectsAdapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        subjectsAdapter.stopListening()
    }

    val subjectsAdapter: FirestoreSubjectAdapter by lazy {
        FirestoreSubjectAdapter(
                subjects.options,
                this
        )
    }
}