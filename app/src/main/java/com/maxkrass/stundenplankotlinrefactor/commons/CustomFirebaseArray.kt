package com.maxkrass.stundenplankotlinrefactor.commons

import android.util.SparseArray
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.maxkrass.stundenplankotlinrefactor.data.Lesson
import com.maxkrass.stundenplankotlinrefactor.data.Lessons
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Subjects


/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */
const val MAX_NUMBER_OF_LESSONS = 10

internal class CustomFirebaseArray(private val mLessonQuery: Query, private val mUid: String?) : ValueEventListener {
    private var mListener: ((Lessons, Subjects) -> Unit)? = null
    private val mLessons: SparseArray<Lesson> = SparseArray(MAX_NUMBER_OF_LESSONS)

    init {
        mLessonQuery.addValueEventListener(this)
    }//mLessonQuery.orderByValue();

    fun cleanup() {
        mLessonQuery.removeEventListener(this)
    }

    val count: Int
        get() = mLessons.size()

    fun getItem(key: Int): Lesson? {
        return mLessons.get(key)
    }

    fun getItemByIndex(index: Int): Lesson? {
        return mLessons.valueAt(index)
    }

    fun setOnChangedListener(listener: (Lessons, Subjects) -> Unit) {
        mListener = listener
    }

    private fun notifyChangedListeners(subjects: Subjects) {
        mListener?.invoke(mLessons, subjects)
    }

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        mLessons.clear()
        if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
            dataSnapshot.children
                    .mapNotNull { it.getValue(Lesson::class.java) }
                    .forEach { mLessons.put(it.period, it) }
            mLessonQuery
                    .ref
                    .root
                    .child("subjects")
                    .child(mUid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(subjectSnapshot: DataSnapshot) {
                            val mSubjects = Subjects()
                            if (subjectSnapshot.exists() && subjectSnapshot.hasChildren()) {
                                subjectSnapshot.children
                                        .mapNotNull { it.getValue<Subject>(Subject::class.java) }
                                        .forEach { mSubjects.put(it.name, it) }
                            }
                            notifyChangedListeners(mSubjects)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {

                        }
                    })
        }
    }

    override fun onCancelled(firebaseError: DatabaseError) {}


}
