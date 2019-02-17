package com.maxkrass.stundenplankotlinrefactor.managesubjects

import com.maxkrass.stundenplankotlinrefactor.commons.IManageSubjectsView
import com.maxkrass.stundenplankotlinrefactor.createsubject.SubjectRepository
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Uid
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView

class ManageSubjectsPresenter(uid: Uid) :
        TiPresenter<IManageSubjectsView>(),
        FirestoreSubjectAdapter.SubjectViewHolder.Host {

    private val subjects = SubjectRepository(uid)

    override fun onSubjectClicked(subject: Subject) {
        deliverToView {
            showSubjectDetails(subject)
        }
    }

    override fun onSubjectLongClicked(subject: Subject): Boolean {
        return view?.showLongClickDialog(subject) ?: false
    }

    fun restore(subject: Subject) {
        subjects.restore(subject)
    }

    fun delete(subject: Subject) {
        subjects.delete(subject)
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
