package com.maxkrass.stundenplankotlinrefactor.createsubject

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.maxkrass.stundenplankotlinrefactor.commons.CreateSubjectView
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.thetechnocafe.gurleensethi.liteutils.Validator
import net.grandcentrix.thirtyinch.TiPresenter

/**
 * Max made this for Stundenplan2 on 20.07.2016.
 */
class CreateSubjectPresenter<V : CreateSubjectView>(private val subjectRepository: SubjectRepository,
                                                    private val mSubjectKey: String?) : TiPresenter<V>(), OnCompleteListener<DocumentSnapshot> {

    private val isNewSubject: Boolean
        get() = mSubjectKey?.isBlank() != false

    override fun onCreate() {
        super.onCreate()
        if (!isNewSubject) {
            subjectRepository.getSubject(mSubjectKey as String, this)
        }
    }

    fun validateSubject(name: Validator, abbreviation: Validator, color: String, teacher: String) {
        view?.removeErrors()
        if (validateName(name) and validateAbbreviation(abbreviation)) {
            if (isNewSubject || subjectNameWasChanged(name.text)) {
                subjectRepository.subjectExists(name.text) { task ->
                    if (task.isSuccessful && task.result.exists()) {
                        view?.subjectAlreadyExists()
                    } else {
                        saveSubject(name.text, abbreviation.text, color, teacher)
                    }
                }
            } else {
                saveSubject(name.text, abbreviation.text, color, teacher)
            }
        }
    }

    private fun validateName(name: Validator): Boolean {
        return name.atLeastOneUpperCase()
                .noNumbers()
                .noSpecialCharacter()
                .nonEmpty()
                .addErrorCallback {
                    view?.nameInvalid()
                }.validate()
    }

    private fun validateAbbreviation(abbreviation: Validator): Boolean {
        return abbreviation.nonEmpty()
                .noSpecialCharacter()
                .maximumLength(5)
                .addErrorCallback {
                    view?.abbreviationInvalid()
                }.validate()
    }

    private fun saveSubject(name: String, abbreviation: String, color: String, teacher: String) {
        val listener = OnCompleteListener<Void> { task ->
            if (task.isSuccessful)
                view?.exitCreateDialog()
            else
                view?.savingFailed()
        }

        if (isNewSubject) {
            subjectRepository.createSubject(name, abbreviation, color, teacher, listener)
        } else if (null != mSubjectKey) {
            subjectRepository.updateSubject(mSubjectKey,
                                            name,
                                            abbreviation,
                                            color,
                                            teacher,
                                            listener)
        }
    }

    private fun subjectNameWasChanged(name: String): Boolean = name != mSubjectKey

    override fun onComplete(task: Task<DocumentSnapshot>) {
        if (task.isSuccessful) {
            view?.showSubject(task.result.toObject(Subject::class.java))
        } else {
            view?.exitCreateDialog()
        }
    }
}
