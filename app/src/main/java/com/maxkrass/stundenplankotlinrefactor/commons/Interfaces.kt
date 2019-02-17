package com.maxkrass.stundenplankotlinrefactor.commons

import android.util.SparseArray
import com.maxkrass.stundenplankotlinrefactor.data.*
import com.maxkrass.stundenplankotlinrefactor.manageteachers.FirebaseTeacherAdapter
import net.grandcentrix.thirtyinch.TiView

/**
 * Max made this for StundenplanKotlinRefactor on 24.05.2017.
 */

interface IStundenplanView : TiView {

    fun updateWeekdayColumn(lessons: Lessons, subjects: Subjects)

    fun showLoadingError()
}

interface BasePresenter<in T : TiView> {

    fun initialize()

    fun setView(view: T)
}

interface IStundenplanPresenter : BasePresenter<IStundenplanView>

interface BaseInteractorPresenter

interface BaseInteractor<T : BaseInteractorPresenter>

interface IStundenplanInteractor {

    interface OnLessonLoadedListener {

        fun onLessonLoaded(lessons: SparseArray<Lesson>)

        fun onLoadingError()
    }

    fun loadLessons(onLessonLoadedListener: OnLessonLoadedListener)

    interface OnLessonsLoadedListener {

        fun onLessonsLoaded(lessons: Lessons, subjects: Subjects)

        fun onLoadingError()
    }

    fun loadLessons(weekday: Weekday, onLessonsLoadedListener: OnLessonsLoadedListener)
}

interface CreateSubjectView : TiView {

    fun nameInvalid()

    fun subjectAlreadyExists()

    fun abbreviationInvalid()

    fun removeErrors()

    fun savingFailed()

    fun exitCreateDialog()

    fun showSubject(subject: Subject)
}

interface CreateLessonView : TiView {

    fun removeErrors()

    fun savingFailed()

    fun exitCreateDialog()

    fun onSubjectChosen(subject: Subject)

    fun onNoSubjectChosen()
}

interface ChooseSubjectView : TiView {

    fun onSubjectChosen(subject: Subject)

    fun showSubjectLongClick(subject: Subject)
}

interface IManageSubjectsView : TiView {

    fun showLongClickDialog(subject: Subject): Boolean

    fun showSubjectDetails(subject: Subject)
}

interface ChooseTeacherView : TiView {

    fun onTeacherChosen(teacher: Teacher)
}

interface ManageTeachersView : TiView {

    fun onTeacherClicked(teacher: Teacher, viewHolder: FirebaseTeacherAdapter.TeacherViewHolder)

    // fun onTeacherLongClicked(teacher: Teacher): Boolean

    fun onTeacherEmailClicked(teacher: Teacher)
}

/**
 * Max made this for StundenplanKotlinRefactor on 14.12.2017.
 */

interface SettingsView : TiView

/**
 * Max made this for StundenplanKotlinRefactor on 06.12.2017.
 */

interface SubstitutionPlanView : TiView