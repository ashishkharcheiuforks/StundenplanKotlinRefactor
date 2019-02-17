package com.maxkrass.stundenplankotlinrefactor.createlesson

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.CreateLessonView
import com.maxkrass.stundenplankotlinrefactor.data.Lesson
import com.maxkrass.stundenplankotlinrefactor.data.LessonWeekday
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Uid
import com.maxkrass.stundenplankotlinrefactor.extensions.longSnackbar
import com.maxkrass.stundenplankotlinrefactor.utils.validator
import kotlinx.android.synthetic.main.fragment_create_lesson.*
import net.grandcentrix.thirtyinch.TiFragment
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.textResource

/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */

class CreateLessonFragment : TiFragment<CreateLessonPresenter<CreateLessonView>, CreateLessonView>(), CreateLessonView {

    override fun onNoSubjectChosen() {
        select_subject.textResource = R.string.select_subject
        select_subject_color.backgroundResource = R.drawable.color_icon_unselected
    }

    override fun onSubjectChosen(subject: Subject) {
        select_subject.text = subject.name
        select_subject_color.background = (ContextCompat.getDrawable(requireContext(), R.drawable.color_icon) as GradientDrawable)
                .apply {
                    setColor(subject.colorInt)
                }
    }

    private val subjectChosenObserver = Observer<Subject?> {
        presenter.onSubjectChosen(it)
    }

    override fun savingFailed() {
        view?.longSnackbar("Saving Failed!")
    }

    override fun exitCreateDialog() {
        activity?.finish()
    }

    override fun removeErrors() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun providePresenter(): CreateLessonPresenter<CreateLessonView> = CreateLessonPresenter()

    override fun onStart() {
        super.onStart()
        CreateLessonActivity.subjectChosenEvent.observe(this, subjectChosenObserver)
    }

    override fun onStop() {
        super.onStop()
        CreateLessonActivity.subjectChosenEvent.removeObserver(subjectChosenObserver)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_create_lesson, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        select_subject_container.onClick {
            NavHostFragment.findNavController(this@CreateLessonFragment).navigate(R.id.action_choose_subject)
        }
        CreateLessonActivity.mainActionClickEvent.observe(this, Observer {
            presenter.validateLesson(lesson_weekday.selectedItem as LessonWeekday, lesson_period.selectedItemPosition, double_period.isChecked, lesson_room.validator())
        })
    }

    companion object {
        fun getInstance(uid: Uid, lesson: Lesson?, doublePeriod: Boolean): CreateLessonFragment {
            val createLessonFragment = CreateLessonFragment()

            val bundle = bundleOf("doublePeriod" to doublePeriod,
                    "uid" to uid,
                    "lesson" to lesson)
            createLessonFragment.arguments = bundle
            return createLessonFragment
        }
    }
}
