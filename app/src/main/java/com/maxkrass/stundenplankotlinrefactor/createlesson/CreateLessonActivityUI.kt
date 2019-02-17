package com.maxkrass.stundenplankotlinrefactor.createlesson

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import com.google.android.material.appbar.AppBarLayout
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.extensions.*
import org.jetbrains.anko.*

/**
 * Max made this for StundenplanKotlinRefactor on 25.07.2017.
 */
@Suppress("MagicNumber")
class CreateLessonActivityUI : AnkoComponent<CreateLessonActivity> {
    override fun createView(ui: AnkoContext<CreateLessonActivity>): View = with(ui) {
        coordinatorLayout {

            frameLayout {
                // id = R.id.main_fragment_container
            }.lparams(matchParent, matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }

            appBarLayout {

                relativeLayout {

                    view {
                        id = R.id.revealBackground
                        backgroundResource = R.color.colorPrimary
                        elevation = dip(4).toFloat()
                        topPadding = dip(24)
                    }.lparams(width = matchParent, height = dip(80))

                    view {
                        id = R.id.reveal
                        backgroundResource = R.color.colorPrimary
                        elevation = dip(4).toFloat()
                        topPadding = dip(24)
                    }.lparams(width = matchParent, height = dip(80))

                    toolbar {
                        id = R.id.create_lesson_toolbar
                        backgroundColor = android.R.color.transparent
                        elevation = dip(4).toFloat()
                        topPadding = dip(24)

                        imageView(R.drawable.ic_clear_24dp) {
                            id = R.id.cancel_lesson
                            backgroundResource = android.R.attr.selectableItemBackgroundBorderless
                            isClickable = true
                            endPadding = dip(16)
                            startPadding = dip(16)
                            imageTintList = ColorStateList.valueOf(0x00_00_00)
                        }.lparams(width = wrapContent, height = matchParent)

                        textView(R.string.new_lesson_title) {
                            id = R.id.create_lesson_title
                            textAppearanceResource = R.style.TextAppearance_MaterialComponents_Headline6
                        }.lparams(width = wrapContent, height = wrapContent) {
                            marginStart = dip(16)
                        }

                        textView(R.string.action_save) {
                            id = R.id.save_lesson
                            backgroundResource = android.R.attr.selectableItemBackgroundBorderless
                            isClickable = true
                            padding = dip(16)
                            textAppearanceResource = R.style.TextAppearance_MaterialComponents_Button
                            gravity = Gravity.CENTER
                        }.lparams(width = wrapContent, height = wrapContent, gravity = Gravity.END)

                        TODO("Finish this Layout")
                    }.lparams(width = matchParent, height = dip(80))
                }
            }
        }
    }
}
