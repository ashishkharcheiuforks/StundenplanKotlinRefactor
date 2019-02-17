package com.maxkrass.stundenplankotlinrefactor.settings

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.extensions.recyclerView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange

@Suppress("MagicNumber")
class SettingsUI : AnkoComponent<SettingsFragment> {
    override fun createView(ui: AnkoContext<SettingsFragment>): View = with(ui) {
        scrollView {
            verticalLayout {
                bottomPadding = dip(16)

                textView(R.string.my_saved_subjects) {
                    textColor = R.color.material_teal
                }.lparams {
                    marginStart = dip(16)
                    topMargin = dip(16)
                }

                textView(R.string.visible_substitution_plans) {
                    textColor = R.color.material_teal
                }.lparams {
                    marginStart = dip(16)
                    topMargin = dip(16)
                }

                checkBox(R.string.my_subjects) {
                    id = R.id.check_box_show_my_subjects
                    setOnCheckedChangeListener(owner.presenter.createCheckedListener(MY_SUBJECTS))
                }.lparams {
                    marginStart = dip(16)
                    topMargin = dip(16)
                }

                checkBox(R.string.ef) {
                    id = R.id.check_box_show_ef
                    setOnCheckedChangeListener(owner.presenter.createCheckedListener(EF))
                }.lparams {
                    marginStart = dip(16)
                    topMargin = dip(8)
                }

                checkBox(R.string.q1) {
                    id = R.id.check_box_show_q1
                    setOnCheckedChangeListener(owner.presenter.createCheckedListener(Q1))
                }.lparams {
                    marginStart = dip(16)
                    topMargin = dip(8)
                }

                checkBox(R.string.q2) {
                    id = R.id.check_box_show_q2
                    onCheckedChange { buttonView, isChecked -> owner.presenter.createCheckedListener(Q2).invoke(buttonView, isChecked) }
                }.lparams {
                    marginStart = dip(16)
                    topMargin = dip(8)
                }

                textView(R.string.notification_settings) {
                    textColor = R.color.colorPrimary
                }.lparams {
                    marginStart = dip(16)
                    topMargin = dip(16)
                }

                switch {
                    textResource = R.string.substitutions_receive_notification
                    isChecked = true
                    id = R.id.substitution_notification_switch
                }

                recyclerView {
                    id = R.id.substitution_subjects_list
                    layoutManager = object : LinearLayoutManager(ctx) {
                        override fun canScrollVertically(): Boolean = false
                    }
                    setHasFixedSize(false)
                }.lparams(width = dip(0), height = wrapContent)
            }.lparams(width = matchParent, height = wrapContent)

            lparams(width = matchParent, height = matchParent)
        }
    }
}