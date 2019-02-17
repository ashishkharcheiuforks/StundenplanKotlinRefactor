package com.maxkrass.stundenplankotlinrefactor.createsubject

import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Subjects
import com.maxkrass.stundenplankotlinrefactor.data.TeacherSubjects

/**
 * Max made this for Stundenplan2 on 20.07.2016.
 */
class SubjectRepository(uId: String = FirebaseAuth.getInstance().uid
        ?: throw IllegalStateException()) {

    private val subjectRef: CollectionReference
    private val mTeachersRef: CollectionReference

    init {
        val userDocumentRef = FirebaseFirestore.getInstance().collection("users").document(uId)

        this.subjectRef = userDocumentRef.collection("subjects")
        this.mTeachersRef = userDocumentRef.collection("teachers")
    }

    fun getSubject(name: String, onCompleteListener: (Task<DocumentSnapshot>) -> Unit) {
        subjectRef.document(name).get().addOnCompleteListener(onCompleteListener)
    }

    fun subjectExists(name: String, onCompleteListener: (Task<DocumentSnapshot>) -> Unit) {
        subjectRef.document(name).get().addOnCompleteListener(onCompleteListener)
    }

    fun updateSubject(key: String, subject: Subject, listener: OnCompleteListener<Void>) {
        if (key != subject.name) {
            subjectRef
                    .document(key)
                    .delete()
        }

        subjectRef
                .document(key)
                .set(subject)
                .addOnCompleteListener(listener)

        addSubjectToTeacher(subject.teacher, subject.name)
    }

    fun createSubject(subject: Subject, listener: OnCompleteListener<Void>) {
        subjectRef
                .document(subject.name)
                .set(subject.toMap())
                .addOnCompleteListener(listener)

        // addSubjectToTeacher(teacher, name);
    }

    private fun addSubjectToTeacher(teacher: String, subject: String) {
        if (!teacher.isEmpty()) {
            mTeachersRef.document(teacher).collection("subjects").get().addOnSuccessListener { documentSnapshots ->
                val subjects = documentSnapshots.toObjects(String::class.java)
                subjects.remove(subject)
                subjects.add(subject)
                // mTeachersRef.document(teacher).collection("subjects").(subjects);
            }
        }
    }

    fun delete(subject: Subject) {
        subjectRef.document(subject.name).delete()
        if (subject.teacher.isNotBlank()) {
            mTeachersRef
                    .document(subject.teacher)
        }
    }

    fun restore(subject: Subject) {
        subjectRef
                .document(subject.name)
                .set(subject.toMap())
        if (subject.teacher.isNotBlank()) {
            mTeachersRef
                    .document(subject.teacher)
                    .get()
                    .addOnSuccessListener { result ->
                        val subjects = result.get("subjects") as TeacherSubjects
                        subjects[subject.name] = true
                        mTeachersRef
                                .document(subject.teacher)
                                .update("subjects", subjects)
                    }
        }
    }

    fun getAllSubjects(): Subjects {
        return Tasks.await(subjectRef.get()).associate {
            val subject: Subject = it.toObject(Subject::class.java)

            subject.name to subject
        }.toMutableMap()
    }

    val options: FirestoreRecyclerOptions<Subject> = FirestoreRecyclerOptions.Builder<Subject>()
            .setQuery(subjectRef, Subject::class.java)
            .build()

}
