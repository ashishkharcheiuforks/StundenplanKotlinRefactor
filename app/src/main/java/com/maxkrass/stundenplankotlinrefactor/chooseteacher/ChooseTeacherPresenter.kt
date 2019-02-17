package com.maxkrass.stundenplankotlinrefactor.chooseteacher

import com.maxkrass.stundenplankotlinrefactor.commons.ChooseTeacherView
import com.maxkrass.stundenplankotlinrefactor.data.Teacher
import com.maxkrass.stundenplankotlinrefactor.data.TeacherRepository
import com.maxkrass.stundenplankotlinrefactor.manageteachers.FirebaseTeacherAdapter
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView

class ChooseTeacherPresenter : TiPresenter<ChooseTeacherView>(), FirebaseTeacherAdapter.TeacherViewHolder.Host {

    override fun onTeacherEmailClicked(teacher: Teacher) {
        deliverToView {
            // onTeacherEmailClicked(teacher)
        }
    }

    override fun onTeacherClicked(teacher: Teacher, viewHolder: FirebaseTeacherAdapter.TeacherViewHolder) {
        deliverToView { onTeacherChosen(teacher) }
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
