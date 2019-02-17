package com.maxkrass.stundenplankotlinrefactor.substitution

import android.view.View
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionSubject
import com.maxkrass.stundenplankotlinrefactor.extensions.bind
import org.jetbrains.anko.*

/**
 * Max made this for StundenplanKotlinRefactor on 20.12.2017.
 */

@Suppress("MagicNumber")
class SubstitutionSubjectUI : AnkoComponent<SubstitutionSubjectListItem> {

    val substitutionSubject = Binder(SubstitutionSubject())

    override fun createView(ui: AnkoContext<SubstitutionSubjectListItem>): View = with(ui) {
        relativeLayout {

            textView {
                id = R.id.substitution_subjects_name
                bind(substitutionSubject) {
                    text = "${it.grade}: ${it.subject}"
                }
                textAppearance = R.style.TextAppearance_AppCompat_Subhead
            }.lparams {
                bottomMargin = dip(16)
                marginStart = dip(16)
                topMargin = dip(16)
                centerVertically()
            }

            themedImageButton(R.drawable.ic_clear_24dp, R.style.SelectableBorderless) {
                id = R.id.substitution_subjects_delete
            }.lparams(width = dip(24), height = dip(24)) {
                marginEnd = dip(16)
                topMargin = dip(12)
                alignParentEnd()
            }
        }
    }
}