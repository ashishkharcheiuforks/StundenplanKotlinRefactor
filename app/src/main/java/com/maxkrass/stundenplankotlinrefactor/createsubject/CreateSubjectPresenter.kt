package com.maxkrass.stundenplankotlinrefactor.createsubject

import com.google.android.gms.tasks.OnCompleteListener
import com.maxkrass.stundenplankotlinrefactor.commons.CreateSubjectView
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Teacher
import com.maxkrass.stundenplankotlinrefactor.utils.Validator
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView

/**
 * Max made this for Stundenplan2 on 20.07.2016.
 */
class CreateSubjectPresenter<V : CreateSubjectView>(
        private val subjectRepository: SubjectRepository,
        private val mSubjectKey: String?
) : TiPresenter<V>() {

    private val isNewSubject: Boolean
        get() = mSubjectKey?.isBlank() ?: true

    override fun onCreate() {
        super.onCreate()
        if (!isNewSubject) {
            subjectRepository.getSubject(mSubjectKey as String) {
                if (it.isSuccessful) {
                    deliverToView {
                        showSubject(it.result?.toObject(Subject::class.java) ?: Subject())
                    }
                } else {
                    deliverToView { exitCreateDialog() }
                }
            }
        }
    }

    fun validateSubject(name: Validator, abbreviation: Validator, color: String, teacher: String) {
        deliverToView { removeErrors() }
        if (validateName(name) and validateAbbreviation(abbreviation)) {
            if (isNewSubject || subjectNameWasChanged(name.text)) {
                subjectRepository.subjectExists(name.text) { task ->
                    if (task.isSuccessful && task.result!!.exists()) {
                        deliverToView { subjectAlreadyExists() }
                    } else {
                        saveSubject(Subject(name.text, abbreviation.text, teacher, color))
                    }
                }
            } else {
                saveSubject(Subject(name.text, abbreviation.text, teacher, color))
            }
        }
    }

    private fun validateName(name: Validator): Boolean {
        return name.atLeastOneUpperCase()
                .noNumbers()
                .noSpecialCharacter()
                .nonEmpty()
                .addErrorCallback {
                    deliverToView { nameInvalid() }
                }.validate()
    }

    private fun validateAbbreviation(abbreviation: Validator): Boolean {
        return abbreviation.nonEmpty()
                .noSpecialCharacter()
                .maximumLength(5)
                .addErrorCallback {
                    deliverToView { abbreviationInvalid() }
                }.validate()
    }

    private fun saveSubject(subject: Subject) {
        val listener = OnCompleteListener<Void> { task ->
            if (task.isSuccessful)
                deliverToView { exitCreateDialog() }
            else
                deliverToView { savingFailed() }
        }

        if (isNewSubject) {
            subjectRepository.createSubject(subject, listener)
        } else if (null != mSubjectKey) {
            subjectRepository.updateSubject(mSubjectKey,
                    subject,
                    listener)
        }
    }

    private fun subjectNameWasChanged(name: String): Boolean = name != mSubjectKey

    fun teacherChosen(teacher: Teacher?) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}
