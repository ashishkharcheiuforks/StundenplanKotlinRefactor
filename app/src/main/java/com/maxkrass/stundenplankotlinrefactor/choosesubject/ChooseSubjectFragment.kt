package com.maxkrass.stundenplankotlinrefactor.choosesubject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.ChooseSubjectView
import com.maxkrass.stundenplankotlinrefactor.createlesson.CreateLessonActivity
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.extensions.inflate
import com.maxkrass.stundenplankotlinrefactor.extensions.startActivity
import com.maxkrass.stundenplankotlinrefactor.viewsubject.ViewSubjectActivity
import net.grandcentrix.thirtyinch.TiFragment
import org.jetbrains.anko.find
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ChooseSubjectFragment : TiFragment<ChooseSubjectPresenter, ChooseSubjectView>(), ChooseSubjectView, KodeinAware {

    override val kodein by kodein()

    private val auth: FirebaseAuth by kodein.instance()

    override fun showSubjectLongClick(subject: Subject) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    fun showSubjectDetails(subject: Subject) {
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

    override fun onSubjectChosen(subject: Subject) {
        mOnSubjectChosenListener?.onSubjectChosen(subject)
    }

    override fun providePresenter() = ChooseSubjectPresenter(auth.currentUser?.uid
            ?: throw IllegalStateException("User mustn't be null"))

    private val mOnSubjectChosenListener: OnSubjectChosenListener? by lazy {
        activity as? OnSubjectChosenListener ?: throw
        ClassCastException(activity.toString() + " must implement OnTeacherChosenListener")
    }

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
    }

    override fun onStart() {
        super.onStart()
        CreateLessonActivity.addSubjectFabClickEvent.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(R.id.action_create_subject)
        })
        CreateLessonActivity.mainActionClickEvent.observe(this, Observer {
            mOnSubjectChosenListener?.onSubjectChosen(null)
        })
    }

    override fun onStop() {
        super.onStop()
        CreateLessonActivity.addSubjectFabClickEvent.removeObservers(this)
        CreateLessonActivity.mainActionClickEvent.removeObservers(this)
    }

    /*fun showLongClickDialog(subject: Subject): Boolean {
        alert(Android) {
            title = subject.name
            items(resources.getStringArray(R.array.dialog_options).asList())
            { dialog: DialogInterface, index: Int ->
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
    }*/

    interface OnSubjectChosenListener {
        fun onSubjectChosen(subject: Subject?)
    }
}