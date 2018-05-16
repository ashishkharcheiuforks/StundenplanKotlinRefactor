package com.maxkrass.stundenplankotlinrefactor.createsubject

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.ViewAnimationUtils
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.BaseActivity
import com.maxkrass.stundenplankotlinrefactor.data.Teacher
import com.maxkrass.stundenplankotlinrefactor.extensions.addFragment
import com.maxkrass.stundenplankotlinrefactor.extensions.inTransaction
import com.maxkrass.stundenplankotlinrefactor.manageteachers.ManageTeachersFragment
import kotlinx.android.synthetic.main.activity_create_subject.*


/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */
const val CREATE_SUBJECT_TAG: String = "create_subject_fragment"

class CreateSubjectActivity : BaseActivity(), CreateSubjectFragment.OnChooseListener, ManageTeachersFragment.OnTeacherChosenListener {

    private val createSubjectFragment: CreateSubjectFragment by lazy {
        var fragment: CreateSubjectFragment? = supportFragmentManager.findFragmentByTag(
                CREATE_SUBJECT_TAG) as? CreateSubjectFragment
        if (fragment == null) {
            fragment = CreateSubjectFragment().apply {
                arguments = Bundle().apply {
                    putString("uid", uid)
                    putString("subjectKey", intent.getStringExtra("subjectKey"))
                }
            }
            addFragment(fragment,
                        R.id.fragment_container,
                        CREATE_SUBJECT_TAG)
        }
        return@lazy fragment!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_subject)

        //((View) mToolbar.getParent()).setPadding(0, Tools.getStatusBarHeight(this), 0, 0);
        cancel_subject.setOnClickListener(this)
        save_subject.setOnClickListener(createSubjectFragment)
    }

    override fun onClick(view: View) {
        super.onClick(view)
        when (view.id) {
            R.id.cancel_subject -> finish()
        }
    }

    private fun animateAppAndStatusBar(fromColor: Int, toColor: Int) {
        revealBackground.setBackgroundColor(fromColor)

        with(ViewAnimationUtils.createCircularReveal(
                reveal,
                create_subject_toolbar.width / 2,
                create_subject_toolbar.height / 2,
                0f,
                create_subject_toolbar.width / 2f)) {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    reveal.setBackgroundColor(toColor)
                }
            })
            startDelay = 200
            duration = 125
            start()
        }

        reveal.visibility = View.VISIBLE
    }

    override fun onShowChosenColor(fromColor: String, toColor: String) {
        animateAppAndStatusBar(Color.parseColor(fromColor), Color.parseColor(toColor))
    }

    override fun onRequestChooseTeacher() {
        val manageTeachersFragment = ManageTeachersFragment()

        supportFragmentManager.inTransaction {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            hide(createSubjectFragment)
            add(R.id.second_fragment_container, manageTeachersFragment)
            addToBackStack(null)
        }

        create_subject_title.setText(R.string.choose_teacher)
        save_subject.text = getString(R.string.action_none)
        save_subject.setOnClickListener { onTeacherChosen(null) }
        cancel_subject.setImageResource(R.drawable.ic_arrow_back_24dp)
        cancel_subject.setOnClickListener { restoreView() }
    }

    override fun onTeacherChosen(teacher: Teacher?) {
        restoreView()
        createSubjectFragment.onTeacherChosen(teacher)
    }

    private fun restoreView() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.inTransaction {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(R.id.fragment_container, createSubjectFragment)
        }
        create_subject_title.setText(R.string.new_subject)
        save_subject.text = getString(R.string.action_save)
        save_subject.setOnClickListener(createSubjectFragment)
        cancel_subject.setImageResource(R.drawable.ic_clear_24dp)
        cancel_subject.setOnClickListener(this)
    }

}
