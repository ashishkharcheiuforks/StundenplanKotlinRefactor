package com.maxkrass.stundenplankotlinrefactor.gradecalculator

import com.maxkrass.stundenplankotlinrefactor.createsubject.SubjectRepository
import com.maxkrass.stundenplankotlinrefactor.data.Uid
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView

/**
 * Max made this for StundenplanKotlinRefactor on 10.12.2017.
 */

class GradeCalculatorPresenter(uid: Uid) : TiPresenter<GradeCalculatorContract.View>() {

    private val subjects = SubjectRepository(uid)

    override fun onAttachView(view: GradeCalculatorContract.View) {
        super.onAttachView(view)

        val allSubjects = subjects.getAllSubjects()

        deliverToView {
            subjectsLoaded(allSubjects)
        }
    }
}
