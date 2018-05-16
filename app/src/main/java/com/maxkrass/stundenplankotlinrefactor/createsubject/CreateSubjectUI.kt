package com.maxkrass.stundenplankotlinrefactor.createsubject

import android.text.InputFilter
import android.view.Gravity
import android.view.View
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.extensions.bind
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Max made this for StundenplanKotlinRefactor on 10.10.2017.
 */
class CreateSubjectUI(private val bindSubject: Binder<Subject>) : AnkoComponent<CreateSubjectFragment> {
    override fun createView(ui: AnkoContext<CreateSubjectFragment>): View = with(ui) {
        verticalLayout {
            padding = dip(16)

            textInputLayout {

                editText {

                    id = R.id.subject_name
                    hintResource = R.string.subject_name
                    bind(bindSubject) {
                        setText(it.name)
                    }

                }

            }.lparams(width = matchParent)

            textInputLayout {

                isCounterEnabled = true
                counterMaxLength = 5

                editText {

                    id = R.id.subject_abbr
                    hintResource = R.string.subject_abbr
                    setEms(3)
                    filters = arrayOf(InputFilter.LengthFilter(5))
                    bind(bindSubject) {
                        setText(it.abbreviation)
                    }

                }

            }.lparams(width = wrapContent)

            themedFrameLayout(R.style.Selectable) {

                id = R.id.select_teacher

                imageView(R.drawable.ic_person_24dp)
                        .lparams(width = dip(24), height = dip(24)) {
                            gravity = Gravity.CENTER_VERTICAL
                        }

                themedTextView(R.string.choose_color, R.style.NotSelectable) {
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

                onClick { owner.mOnChooseListener?.onRequestChooseTeacher() }

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

                onClick { owner.selectColor() }

            }.lparams(width = matchParent, height = dip(48))
        }
    }
}