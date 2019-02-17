package com.maxkrass.stundenplankotlinrefactor.stundenplan

import android.util.SparseArray
import androidx.core.util.isNotEmpty
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.maxkrass.stundenplankotlinrefactor.commons.IStundenplanInteractor
import com.maxkrass.stundenplankotlinrefactor.data.Lesson
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Subjects
import com.maxkrass.stundenplankotlinrefactor.data.Weekday
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Max made this for StundenplanKotlinRefactor on 24.05.2017.
 */

class StundenplanInteractor : IStundenplanInteractor, AnkoLogger {

    private val mUid by lazy {
        FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }

    private val mLessonRef by lazy {
        FirebaseDatabase
                .getInstance()
                .reference
                .child("lessons")
                .child(mUid)
    }

    override fun loadLessons(onLessonLoadedListener: IStundenplanInteractor.OnLessonLoadedListener) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun loadLessons(
        weekday: Weekday,
        onLessonsLoadedListener: IStundenplanInteractor.OnLessonsLoadedListener
    ) {
        mLessonRef.orderByChild("weekday")
                .equalTo(weekday.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        onLessonsLoadedListener.onLoadingError()
                    }

                    override fun onDataChange(subjectSnapshot: DataSnapshot) {
                        info("data changed")
                        val lessons = SparseArray<Lesson>()
                        subjectSnapshot
                                .children
                                .mapNotNull { it.getValue(Lesson::class.java) }
                                .forEach { lesson -> lessons.put(lesson.period, lesson) }

                        info("${lessons.size()} lesson(s) loaded")
                        if (lessons.isNotEmpty()) {
                            mLessonRef
                                    .root
                                    .child("subjects")
                                    .child(mUid)
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {
                                            onLessonsLoadedListener.onLoadingError()
                                        }

                                        override fun onDataChange(p0: DataSnapshot) {
                                            val subjects: Subjects = mutableMapOf()
                                            p0.children
                                                    .mapNotNull { it.getValue(Subject::class.java) }
                                                    .forEach { subject ->
                                                        subjects[subject.name] = subject
                                                    }
                                            info("${subjects.size} subject(s) loaded")
                                            if (subjects.isNotEmpty()) {
                                                onLessonsLoadedListener
                                                        .onLessonsLoaded(lessons = lessons,
                                                                         subjects = subjects)
                                            }
                                        }
                                    })
                        }
                    }
                })
    }
}
