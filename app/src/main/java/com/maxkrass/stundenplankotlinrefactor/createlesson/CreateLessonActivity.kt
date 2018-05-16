package com.maxkrass.stundenplankotlinrefactor.createlesson

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.fragment.app.FragmentTransaction
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.extensions.findFragmentByTag
import com.maxkrass.stundenplankotlinrefactor.extensions.getBooleanExtra
import com.maxkrass.stundenplankotlinrefactor.extensions.show
import com.maxkrass.stundenplankotlinrefactor.customviews.CheckBoxWidget
import com.maxkrass.stundenplankotlinrefactor.data.Lesson
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.managesubjects.ManageSubjectsFragment
import kotlinx.android.synthetic.main.activity_create_lesson.*


/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */
class CreateLessonActivity : AppCompatActivity(),
                             CreateLessonFragment.OnChooseSubjectListener,
                             ManageSubjectsFragment.OnSubjectChosenListener {

    private var color: Int = 0

    private val createLessonTag = "create_lesson_fragment"

    private val createLessonFragment: CreateLessonFragment by lazy {
        findFragmentByTag(createLessonTag) {
            val newFragment = CreateLessonFragment.getInstance(
                    /*getUid*/ "",
                               intent.getSerializableExtra("lesson") as Lesson?,
                               intent.getBooleanExtra("doublePeriod"))
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_fragment_container, newFragment)
                    .commit()
            newFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_lesson)
        save_lesson.setOnClickListener(createLessonFragment)
        resetCancelClickListener()
        //color = Color.LIGHT_GREEN.getColor(this)
    }

    fun onClick(view: View) {
        if (view is CheckBoxWidget)
            view.toggle()
        else if (view.id == R.id.cancel_lesson)
            NavUtils.navigateUpFromSameTask(this)
    }

    private fun animateAppAndStatusBar(fromColor: Int, toColor: Int) {

        val startDelay = 200L
        val duration = 125L

        val animator = ViewAnimationUtils.createCircularReveal(
                reveal,
                create_lesson_toolbar.width / 2,
                create_lesson_toolbar.height / 2, 0f,
                (create_lesson_toolbar.width / 2).toFloat())

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                reveal.setBackgroundColor(toColor)
            }
        })

        revealBackground.setBackgroundColor(fromColor)
        animator.startDelay = startDelay
        animator.duration = duration
        animator.start()
        reveal.show()
    }

    override fun onSubjectChosen(subject: Subject) {
        restoreView()
        val newColor = subject.colorInt
        animateAppAndStatusBar(color, newColor)
        color = newColor
        createLessonFragment.onSubjectChosen(subject)
    }

    override fun onNoneChosen() {
        restoreView()
        createLessonFragment.onNoSubjectChosen()
    }

    fun onRequestChooseSubject() {
        val manageSubjectsFragment = ManageSubjectsFragment()
        val bundle = Bundle()
        bundle.putBoolean("select", true)
        manageSubjectsFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.main_fragment_container, manageSubjectsFragment)
                .addToBackStack(null)
                .commit()
        create_lesson_title.setText(R.string.choose_subject_title)
        save_lesson.setText(R.string.action_none)
        save_lesson.setOnClickListener { onNoneChosen() }
        cancel_lesson.setImageResource(R.drawable.ic_arrow_back_24dp)
        cancel_lesson.setOnClickListener { restoreView() }
    }

    private fun restoreView() {
        supportFragmentManager.apply {
            popBackStack()
            beginTransaction().apply {
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                replace(R.id.main_fragment_container, createLessonFragment)
            }.commit()
        }
        create_lesson_title.setText(R.string.new_lesson_title)
        save_lesson.setText(R.string.action_save)
        save_lesson.setOnClickListener(createLessonFragment)
        cancel_lesson.setImageResource(R.drawable.ic_clear_24dp)
        resetCancelClickListener()
    }

    private fun resetCancelClickListener() = cancel_lesson.setOnClickListener {
        NavUtils.navigateUpFromSameTask(this)
    }
}
