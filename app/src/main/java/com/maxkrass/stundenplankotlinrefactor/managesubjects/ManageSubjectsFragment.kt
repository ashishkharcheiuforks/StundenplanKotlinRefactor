package com.maxkrass.stundenplankotlinrefactor.managesubjects

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.IManageSubjectsView
import com.maxkrass.stundenplankotlinrefactor.createsubject.CreateSubjectActivity
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Uid
import com.maxkrass.stundenplankotlinrefactor.extensions.*
import com.maxkrass.stundenplankotlinrefactor.main.MainActivity
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import com.maxkrass.stundenplankotlinrefactor.viewsubject.ViewSubjectActivity
import kotlinx.android.synthetic.main.fragment_manage_subjects.*
import org.jetbrains.anko.find
import org.kodein.di.generic.factory

private const val BUTTON_EDIT = 0
private const val BUTTON_DELETE = 1

/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */
class ManageSubjectsFragment :
        MainActivityFragment<ManageSubjectsPresenter, IManageSubjectsView>(),
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
        startActivity<ViewSubjectActivity>("subject" to subject) // (newActivity, options.toBundle())
    }

    private val presenterFactory: (Uid) -> ManageSubjectsPresenter by kodein.factory()

    override fun providePresenter() = presenterFactory(uid)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_manage_subjects, container, false)
                ?: container?.inflate(R.layout.fragment_manage_subjects)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view.find<RecyclerView>(R.id.subjects_recyclerview)) {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.subjectsAdapter
            setHasFixedSize(true)
        }
        MainActivity.fabClickEvent.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(R.id.action_create_subject, null)
        })
    }

    override fun showLongClickDialog(subject: Subject): Boolean {
        alert(Android) {
            title = subject.name
            items(resources.getStringArray(R.array.dialog_options).asList()) { dialog: DialogInterface, index: Int ->
                when (index) {
                    BUTTON_EDIT -> {
                        startActivity<CreateSubjectActivity>("subjectKey" to subject.name)
                        dialog.dismiss()
                    }
                    BUTTON_DELETE -> {
                        subjects_recyclerview.longSnackbar(
                                subject.name + " deleted",
                                "restore"
                        ) { presenter.restore(subject) }.show()

                        dialog.dismiss()
                        presenter.delete(subject)
                    }
                }
            }
        }
        return true
    }
}
