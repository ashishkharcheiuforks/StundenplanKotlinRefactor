package com.maxkrass.stundenplankotlinrefactor.manageteachers

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.maxkrass.stundenplankotlinrefactor.commons.ManageTeachersView
import com.maxkrass.stundenplankotlinrefactor.data.Teacher
import com.maxkrass.stundenplankotlinrefactor.data.TeacherSubjects
import net.grandcentrix.thirtyinch.TiPresenter

/**
 * Max made this for StundenplanKotlinRefactor on 14.10.2017.
 */
class ManageTeachersPresenter : TiPresenter<ManageTeachersView>(), FirebaseTeacherAdapter.TeacherViewHolder.Host {
    override fun onTeacherEmailClicked(teacher: Teacher) {
        view?.onTeacherEmailClicked(teacher)
    }

    //fun onTeacherLongClicked(teacher: Teacher): Boolean =
            //view?.onTeacherLongClicked(teacher) ?: false

    override fun onTeacherClicked(teacher: Teacher,
                                  viewHolder: FirebaseTeacherAdapter.TeacherViewHolder) {
        view?.onTeacherClicked(teacher, viewHolder)
    }

    private val mTeacherRef: DatabaseReference =
            FirebaseDatabase
                    .getInstance()
                    .getReference("stundenplan")
                    .child("publicTeachers")

    override fun onCreate() {
        super.onCreate()
        teachersAdapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        teachersAdapter.stopListening()
    }

    val teachersAdapter: FirebaseTeacherAdapter by lazy {
        val options: FirebaseRecyclerOptions<Teacher> = FirebaseRecyclerOptions.Builder<Teacher>()
                .setQuery(mTeacherRef, { snapshot: DataSnapshot? ->
                    snapshot?.let {
                        Teacher(it.key,
                                it.key,
                                TeacherSubjects())
                    } ?: Teacher("", "", TeacherSubjects())
                })
                .build()
        FirebaseTeacherAdapter(
                options,
                this
        )
    }

}