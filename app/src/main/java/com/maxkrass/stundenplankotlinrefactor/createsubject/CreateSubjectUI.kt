package com.maxkrass.stundenplankotlinrefactor.createsubject

import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.extensions.bind
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * Max made this for StundenplanKotlinRefactor on 10.10.2017.
 */
@Suppress("MagicNumber")
class CreateSubjectUI(private val bindSubject: Binder<Subject>) : AnkoComponent<CreateSubjectFragment> {
    override fun createView(ui: AnkoContext<CreateSubjectFragment>): View = with(ui) {
        verticalLayout {
            padding = dip(16)

            include<RelativeLayout>(R.layout.fragment_create_subject_inputs).lparams(width = matchParent)

            themedFrameLayout(R.style.Selectable) {

                id = R.id.select_teacher

                imageView(R.drawable.ic_person_24dp)
                        .lparams(width = dip(24), height = dip(24)) {
                            gravity = Gravity.CENTER_VERTICAL
                        }

                themedTextView(R.string.choose_teacher, R.style.NotSelectable) {
                    textSize = 16f
                    id = R.id.select_teacher_label
                    bind(bindSubject) {
                        if (it.teacher.isBlank())
                            setText(R.string.select_teacher)
                        else text = it.teacher
                    }
                }.lparams {
                    gravity = Gravity.CENTER_VERTICAL
                    marginStart = dip(56)
                }

                onClick {
                    //owner.mOnChooseListener?.onRequestChooseTeacher()
                }
            }.lparams(width = matchParent, height = dip(48)) {
                topMargin = dip(16)
            }

            themedFrameLayout(R.style.Selectable) {

                id = R.id.choose_color

                view {
                    id = R.id.color_icon
                    backgroundResource = R.drawable.color_icon
                }.lparams(width = dip(24), height = dip(24)) {
                    gravity = Gravity.CENTER_VERTICAL
                }

                themedTextView(R.string.choose_color, R.style.NotSelectable) {
                    textSize = 16f
                    id = R.id.color_name_label
                }.lparams {
                    gravity = Gravity.CENTER_VERTICAL
                    marginStart = dip(56)
                }

                onClick {
                    //owner.selectColor()
                }
            }.lparams(width = matchParent, height = dip(48))
        }
    }
}