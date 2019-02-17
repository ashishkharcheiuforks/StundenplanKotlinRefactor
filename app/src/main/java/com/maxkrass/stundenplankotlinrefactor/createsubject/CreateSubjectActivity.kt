package com.maxkrass.stundenplankotlinrefactor.createsubject

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.BaseActivity
import com.maxkrass.stundenplankotlinrefactor.data.Teacher
import com.maxkrass.stundenplankotlinrefactor.extensions.SingleLiveEvent
import com.maxkrass.stundenplankotlinrefactor.manageteachers.ManageTeachersFragment
import kotlinx.android.synthetic.main.activity_create_subject.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.textResource

/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */
class CreateSubjectActivity :
        BaseActivity(),
        ManageTeachersFragment.OnTeacherChosenListener {

    companion object {
        val teacherChosenEvent = SingleLiveEvent<Teacher?>()
        val mainActionClickEvent = SingleLiveEvent<View>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_subject)

        findNavController(R.id.create_subject_nav_host_fragment)
                .addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        R.id.createSubjectFragment -> {
                            cancel_subject.imageResource = R.drawable.ic_clear_24dp
                            create_subject_title.textResource = R.string.new_subject
                            save_subject.textResource = R.string.action_save
                        }
                        R.id.chooseTeacherFragment -> {
                            cancel_subject.imageResource = R.drawable.ic_arrow_back_24dp
                            create_subject_title.textResource = R.string.select_teacher
                            save_subject.textResource = R.string.action_none
                        }
                    }
                }
        // ((View) mToolbar.getParent()).setPadding(0, Tools.getStatusBarHeight(this), 0, 0);
        resetCancelClickListener()
        save_subject.setOnClickListener { mainActionClickEvent.call(it) }
    }

    private fun resetCancelClickListener() = cancel_subject.onClick {
        findNavController(R.id.create_subject_nav_host_fragment).navigateUp()
    }

    override fun onTeacherChosen(teacher: Teacher?) {
        findNavController(R.id.create_subject_nav_host_fragment).navigateUp()
        teacherChosenEvent.call(teacher)
        restoreView()
    }

    private fun restoreView() {
        create_subject_title.textResource = R.string.new_subject
        save_subject.textResource = R.string.action_save
        // save_subject.setOnClickListener(createSubjectFragment)
        cancel_subject.imageResource = R.drawable.ic_clear_24dp
        cancel_subject.onClick { this@CreateSubjectActivity }
    }
}
