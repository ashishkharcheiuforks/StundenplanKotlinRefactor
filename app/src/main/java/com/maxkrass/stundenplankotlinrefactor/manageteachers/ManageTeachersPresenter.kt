package com.maxkrass.stundenplankotlinrefactor.manageteachers

import com.maxkrass.stundenplankotlinrefactor.commons.ManageTeachersView
import com.maxkrass.stundenplankotlinrefactor.data.Teacher
import com.maxkrass.stundenplankotlinrefactor.data.TeacherRepository
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView

/**
 * Max made this for StundenplanKotlinRefactor on 14.10.2017.
 */
class ManageTeachersPresenter : TiPresenter<ManageTeachersView>(), FirebaseTeacherAdapter.TeacherViewHolder.Host {

    override fun onTeacherEmailClicked(teacher: Teacher) {
        deliverToView { onTeacherEmailClicked(teacher) }
    }

    override fun onTeacherClicked(
            teacher: Teacher,
            viewHolder: FirebaseTeacherAdapter.TeacherViewHolder
    ) {
        deliverToView { onTeacherClicked(teacher, viewHolder) }
    }

    override fun onCreate() {
        super.onCreate()
        teachersAdapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        teachersAdapter.stopListening()
    }

    val teachersAdapter: FirebaseTeacherAdapter by lazy {
        FirebaseTeacherAdapter(
                TeacherRepository.options,
                this
        )
    }
}