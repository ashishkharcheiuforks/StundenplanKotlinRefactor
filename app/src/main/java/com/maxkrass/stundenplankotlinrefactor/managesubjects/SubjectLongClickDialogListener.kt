package com.maxkrass.stundenplankotlinrefactor.managesubjects

import android.content.DialogInterface
import com.maxkrass.stundenplankotlinrefactor.createsubject.CreateSubjectActivity
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.extensions.longSnackbar
import com.maxkrass.stundenplankotlinrefactor.extensions.startActivity
import kotlinx.android.synthetic.main.fragment_manage_subjects.*

private const val BUTTON_EDIT = 0
private const val BUTTON_DELETE = 1

class SubjectLongClickDialogListener(
    private val manageSubjectsFragment: ManageSubjectsFragment,
    private val subject: Subject
) : DialogInterface.OnClickListener {

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            BUTTON_EDIT -> {
                manageSubjectsFragment
                        .startActivity<CreateSubjectActivity>("subjectKey" to subject.name)
                dialog?.dismiss()
            }
            BUTTON_DELETE -> {
                manageSubjectsFragment.subjects_recyclerview.longSnackbar(
                        subject.name + " deleted",
                        "restore"
                ) {
                    manageSubjectsFragment.presenter.restore(subject)
                }.show()

                dialog?.dismiss()
                manageSubjectsFragment.presenter.delete(subject)
            }
        }
    }
}

object SubjectLongClickListener
