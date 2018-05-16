package com.maxkrass.stundenplankotlinrefactor.managesubjects

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.createsubject.CreateSubjectActivity
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.extensions.inflate
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import com.maxkrass.stundenplankotlinrefactor.viewsubject.ViewSubjectActivity
import kotlinx.android.synthetic.main.fragment_manage_subjects.*
import net.grandcentrix.thirtyinch.TiView
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity

private const val BUTTON_EDIT = 0
private const val BUTTON_DELETE = 1

/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */
class ManageSubjectsFragment : MainActivityFragment<ManageSubjectsPresenterFirestore, IManageSubjectsView>(),
                               View.OnClickListener,
                               IManageSubjectsView {
    override fun showSubjectDetails(subject: Subject) {
        /* TODO("Fix Transitions")
        val newActivity = Intent(view.context, ViewSubjectActivity::class.java)
        newActivity.putExtra("subject", subject)
        view.activity.window.exitTransition = Fade().excludeTarget(
                android.R.id.statusBarBackground,
                true)
                .excludeTarget(
                        android.R.id.navigationBarBackground,
                        true)
        val options = ActivityOptions.makeSceneTransitionAnimation(
                activity = view.activity,
                Pair.create(subjectViewHolder.subjectColor, "subject_color"),
                Pair.create(subjectViewHolder.subjectName, "subject_name")
        )*/
        startActivity<ViewSubjectActivity>("subject" to subject)//(newActivity, options.toBundle())
    }

    override fun onSubjectChosen(subject: Subject) {
        mOnSubjectChosenListener?.onSubjectChosen(subject)
    }

    override fun providePresenter() = ManageSubjectsPresenterFirestore(uid, mSelect)

    override val showsTabs: Boolean get() = false
    override val toolbarTitle: String by lazy { getString(R.string.action_subjects) }

    private val mSelect: Boolean by lazy {
        arguments?.getBoolean("select") ?: false
    }
    private val mOnSubjectChosenListener: OnSubjectChosenListener? by lazy {
        if (mSelect) {
            activity as? OnSubjectChosenListener ?: throw
            ClassCastException(activity.toString() + " must implement OnTeacherChosenListener")
        } else {
            null
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_manage_subjects, container, false) ?:
               container?.inflate(R.layout.fragment_manage_subjects)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view?.let {
            with(it.find<RecyclerView>(R.id.subjects_recyclerview)) {
                adapter = presenter.subjectsAdapter
                setHasFixedSize(true)
            }
            it.find<View>(R.id.add_subject).setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
        activity.startActivity(Intent(activity, CreateSubjectActivity::class.java))
    }

    override fun showLongClickDialog(subject: Subject): Boolean {
        alert {
            title = subject.name
            items(resources.getStringArray(R.array.dialog_options).asList())
            { dialog: DialogInterface, index: Int ->
                when (index) {
                    BUTTON_EDIT   -> {
                        startActivity<CreateSubjectActivity>("subjectKey" to subject.name)
                        dialog.dismiss()
                    }
                    BUTTON_DELETE -> {
                        longSnackbar(subjects_recyclerview,
                                     subject.name + " deleted",
                                     "restore",
                                     { presenter.restore(subject) }).show()

                        dialog.dismiss()
                        presenter.delete(subject)
                    }
                }
            }
        }
        return true
    }

    interface OnSubjectChosenListener {
        fun onSubjectChosen(subject: Subject)

        fun onNoneChosen()
    }
}

interface IManageSubjectsView : TiView {

    fun onSubjectChosen(subject: Subject)

    fun showLongClickDialog(subject: Subject): Boolean

    fun showSubjectDetails(subject: Subject)

}

