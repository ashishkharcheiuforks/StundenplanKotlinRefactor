package com.maxkrass.stundenplankotlinrefactor.createlesson

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.choosesubject.ChooseSubjectFragment
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.extensions.SingleLiveEvent
import com.maxkrass.stundenplankotlinrefactor.extensions.show
import kotlinx.android.synthetic.main.activity_create_lesson.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.textResource

/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */
class CreateLessonActivity : AppCompatActivity(), ChooseSubjectFragment.OnSubjectChosenListener {

    companion object {
        val mainActionClickEvent = SingleLiveEvent<View>()
        val addSubjectFabClickEvent = SingleLiveEvent<View>()
        val subjectChosenEvent = SingleLiveEvent<Subject?>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_lesson)

        findNavController(R.id.create_lesson_nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.chooseSubjectFragment -> {
                    cancel_lesson.imageResource = R.drawable.ic_arrow_back_24dp
                    save_lesson.textResource = R.string.action_none
                    create_lesson_title.textResource = R.string.select_subject
                    add_subject_fab.show()
                }
                R.id.createLessonFragment -> {
                    cancel_lesson.imageResource = R.drawable.ic_clear_24dp
                    save_lesson.textResource = R.string.action_save
                    create_lesson_title.textResource = R.string.new_lesson_title
                    add_subject_fab.hide()
                }
            }
        }

        add_subject_fab.setOnClickListener { addSubjectFabClickEvent.call(it) }
        save_lesson.setOnClickListener { mainActionClickEvent.call(it) }

        resetCancelClickListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActionClickEvent.removeObservers(this)
        addSubjectFabClickEvent.removeObservers(this)
        subjectChosenEvent.removeObservers(this)
    }

    override fun onSupportNavigateUp(): Boolean =
            findNavController(R.id.create_lesson_nav_host_fragment).navigateUp()

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

    override fun onSubjectChosen(subject: Subject?) {
        findNavController(R.id.create_lesson_nav_host_fragment).navigateUp()
        subjectChosenEvent.call(subject)
    }

    private fun resetCancelClickListener() = cancel_lesson.onClick {
        findNavController(R.id.create_lesson_nav_host_fragment).navigateUp()
    }
}
