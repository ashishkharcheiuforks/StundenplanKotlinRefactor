package com.maxkrass.stundenplankotlinrefactor.managesubjects

import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.TeacherSubjects
import com.maxkrass.stundenplankotlinrefactor.data.Uid
import net.grandcentrix.thirtyinch.TiPresenter

class ManageSubjectsPresenterFirestore(private val uid: Uid,
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

    private val mUserDocumentRef: DocumentReference by lazy {
        FirebaseFirestore.getInstance().collection("users").document(uid)
    }

    private val mSubjectRef: CollectionReference by lazy {
        mUserDocumentRef.collection("subjects")
    }

    private val mTeachersRef: CollectionReference by lazy {
        mUserDocumentRef.collection("teachers")
    }

    fun restore(subject: Subject) {
        mSubjectRef
                .document(subject.name)
                .set(subject.toMap())
        if (subject.teacher.isNotBlank()) {
            mTeachersRef
                    .document(subject.teacher)
                    .get()
                    .addOnCompleteListener(
                            { task ->
                                if (task.isSuccessful) {
                                    val subjects = task.result.get("subjects") as TeacherSubjects
                                    subjects.put(subject.name, true)
                                    mTeachersRef
                                            .document(subject.teacher)
                                            .update("subjects", subjects)
                                }
                            })
        }
    }

    fun delete(subject: Subject) {
        mSubjectRef.document(subject.name).delete()
        if (subject.teacher.isNotBlank()) {
            mTeachersRef
                    .document(subject.teacher)
        }
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
        val options: FirestoreRecyclerOptions<Subject> = FirestoreRecyclerOptions.Builder<Subject>()
                .setQuery(mSubjectRef, Subject::class.java)
                .build()
        FirestoreSubjectAdapter(
                options,
                this
        )
    }
}
