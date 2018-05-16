package com.maxkrass.stundenplankotlinrefactor.substitution

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.extensions.textAppearanceResource
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Max made this for StundenplanKotlinRefactor on 22.10.2017.
 */
class SubstitutionCard : AnkoComponent<SubstitutionCardItemList> {
    override fun createView(ui: AnkoContext<SubstitutionCardItemList>): View = with(ui) {
        verticalLayout {

            textView {
                id = R.id.substitution_card_header
                textAppearanceResource = R.style.TextAppearance_AppCompat_Headline
            }.lparams(wrapContent, wrapContent) {
                margin = dip(16)
            }

            recyclerView {
                id = R.id.substitution_card_recycler_view
                isHorizontalScrollBarEnabled = false
                isVerticalScrollBarEnabled = false
                layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
            }.lparams(width = matchParent, height = wrapContent)

            lparams(width = matchParent, height = wrapContent) /*{
                marginStart = dip(8)
                marginEnd = dip(8)
                topMargin = dip(4)
                bottomMargin = dip(4)
            }*/

        }

    }

}
