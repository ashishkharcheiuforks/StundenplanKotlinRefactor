package com.maxkrass.stundenplankotlinrefactor.createlesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.data.Lesson
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Uid


/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */

class CreateLessonFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_create_lesson, container, false)

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

    interface OnChooseSubjectListener

    fun onSubjectChosen(subject: Subject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onNoSubjectChosen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
