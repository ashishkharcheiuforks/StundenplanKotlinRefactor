package com.maxkrass.stundenplankotlinrefactor.substitution

import android.content.Context
import android.widget.RelativeLayout
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import org.jetbrains.anko.AnkoContext

/**
 * Max made this for StundenplanKotlinRefactor on 10.12.2017.
 */
class SingleSubstitutionEventListItem(context: Context) : RelativeLayout(context) {

    internal val ui = SingleSubstitutionEventUi()

    init {
        addView(ui.createView(AnkoContext.create(context, this)))
    }

    var data: SubstitutionEvent = SubstitutionEvent()
        set(value) {
            ui.event.item = value
        }
}