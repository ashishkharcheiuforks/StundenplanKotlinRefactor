package com.maxkrass.stundenplankotlinrefactor.managesubjects

import com.google.firebase.database.*
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.TeacherSubjects
import com.maxkrass.stundenplankotlinrefactor.data.Uid
import net.grandcentrix.thirtyinch.TiPresenter


class ManageSubjectsPresenter(private val uid: Uid,
                              private val select: Boolean) : TiPresenter<IManageSubjectsView>(), FirestoreSubjectAdapter.SubjectViewHolder.Host {
    override fun onSubjectClicked(subject: Subject) {
        if (select) {
            view?.onSubjectChosen(subject)
        } else {
            view?.showSubjectDetails(subject)
        }
    }

    override fun onSubjectLongClicked(subject: Subject): Boolean {
        return view?.showLongClickDialog(subject) ?: false
    }

    private val mSubjectRef: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference.child("subjects").child(uid)
    }

    private val mTeachersRef: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference.child("teachers").child(uid)
    }

    fun restore(subject: Subject) {
        mSubjectRef
                .child(subject.name)
                .setValue(subject)
        if (subject.teacher.isNotBlank()) {
            mTeachersRef
                    .child(subject.teacher)
                    .child("subjects")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(subjectSnapshot: DataSnapshot) {
                            val subjects = subjectSnapshot.getValue(
                                    object : GenericTypeIndicator<TeacherSubjects>(){}) ?: HashMap()
                            subjects.put(subject.name, true)
                            mTeachersRef
                                    .child(subject.teacher)
                                    .child("subjects")
                                    .setValue(subjects)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {

                        }
                    })
        }
    }

    fun delete(subject: Subject) {
        mSubjectRef.child(subject.name).removeValue()
        if (subject.teacher.isNotBlank()) {
            mTeachersRef
                    .child(subject.teacher)
                    .child("subjects")
                    .child(subject.name)
                    .removeValue()
        }
    }

    /*override fun onCreate() {
        super.onCreate()
        subjectsAdapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        subjectsAdapter.stopListening()
    }

    val subjectsAdapter: FirestoreSubjectAdapter by lazy {
        val options: FirebaseRecyclerOptions<Subject> = FirebaseRecyclerOptions.Builder<Subject>()
                .setQuery(mSubjectRef, Subject::class.java)
                .build()
        FirestoreSubjectAdapter(
                options,
                this
        )
    }*/
}

