package com.maxkrass.stundenplankotlinrefactor.substitution

import android.content.Context
import android.widget.RelativeLayout
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionSubject
import org.jetbrains.anko.AnkoContext

/**
 * Max made this for StundenplanKotlinRefactor on 20.12.2017.
 */
class SubstitutionSubjectListItem(context: Context) : RelativeLayout(context) {

    private val ui = SubstitutionSubjectUI()

    var data = SubstitutionSubject()
        set(value) {
            ui.substitutionSubject.item = value
        }

    init {
        addView(ui.createView(AnkoContext.create(context, this)))
    }
}