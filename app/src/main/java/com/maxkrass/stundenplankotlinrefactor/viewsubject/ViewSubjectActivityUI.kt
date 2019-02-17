package com.maxkrass.stundenplankotlinrefactor.viewsubject

import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.extensions.appBarLayout
import com.maxkrass.stundenplankotlinrefactor.extensions.coordinatorLayout
import com.maxkrass.stundenplankotlinrefactor.extensions.startContentInset
import com.maxkrass.stundenplankotlinrefactor.extensions.textAppearanceResource
import org.jetbrains.anko.*

/**
 * Max made this for StundenplanKotlinRefactor on 20.09.2017.
 */
@Suppress("MagicNumber")
class ViewSubjectActivityUI : AnkoComponent<ViewSubjectActivity> {
    override fun createView(ui: AnkoContext<ViewSubjectActivity>): View = with(ui) {
        coordinatorLayout {

            frameLayout {

                imageView(R.drawable.ic_person_24dp) {
                    id = R.id.view_subject_teacher_name_icon
                    scaleType = ImageView.ScaleType.CENTER
                    transitionName = "subject_teacher_name"
                }.lparams(width = dip(48), height = dip(48)) {
                    marginStart = dip(4)
                }

                textView {
                    id = R.id.view_subject_teacher_name
                    textSize = 16f
                }.lparams {
                    marginStart = dip(72)
                    gravity = Gravity.CENTER_VERTICAL
                }
            }.lparams {
                behavior = com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior()
            }

            appBarLayout {
                backgroundResource = R.color.material_red
                topPadding = dip(24)
                id = R.id.view_subject_app_bar_layout
                transitionName = "subject_color"

                toolbar {

                    startContentInset = dip(72)

                    lparams(width = matchParent, height = dip(128))

                    frameLayout {

                        textView {
                            id = R.id.view_subject_name
                            textAppearanceResource = R.style.TextAppearance_AppCompat_Widget_ActionBar_Title
                            textSize = 24f
                            transitionName = "subject_name"
                        }.lparams(width = matchParent, gravity = Gravity.CENTER_VERTICAL)

                        lparams(width = matchParent, height = dip(72)) {
                            topMargin = dip(56)
                        }
                    }
                }

                lparams(width = matchParent)
            }

            lparams(width = matchParent, height = matchParent)
        }
    }
}
