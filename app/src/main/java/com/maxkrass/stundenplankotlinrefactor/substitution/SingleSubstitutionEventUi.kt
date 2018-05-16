package com.maxkrass.stundenplankotlinrefactor.substitution

import android.view.View
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import com.maxkrass.stundenplankotlinrefactor.extensions.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.tintedImageView

/**
 * Max made this for StundenplanKotlinRefactor on 29.11.2017.
 */
class SingleSubstitutionEventUi : AnkoComponent<SingleSubstitutionEventListItem> {

    var event: Binder<SubstitutionEvent> = Binder(SubstitutionEvent())

    override fun createView(ui: AnkoContext<SingleSubstitutionEventListItem>): View = with(ui) {
        relativeLayout {

            minimumHeight = dip(64)

            view {
                id = R.id.divider
                backgroundColorResource = R.color.divider_black
            }.lparams(width = 0, height = dip(1)) {
                alignParentTop()
                alignParentStart()
                alignParentEnd()
            }

            tintedImageView {
                id = R.id.substitution_event_icon
                bind(event) {
                    imageResource = it.iconRes
                    imageTintList = it.eventColors
                }
            }.lparams(width = dip(24), height = dip(24)) {
                marginStart = dip(16)
                alignParentStart()
                centerVertically()
            }

            textView {
                id = R.id.substitution_text_upper
                bind(event) {
                    text = it.topText
                }
                maxLines = 1
                textAppearanceResource = R.style.TextAppearance_AppCompat_Subhead
            }.lparams {
                marginEnd = dip(20)
                marginStart = dip(24)
                topMargin = dip(12)
                bottomOf(R.id.divider)
                endOf(R.id.substitution_event_icon)
            }

            textView {
                id = R.id.substitution_text_lower
                bind(event) {
                    text = it.secondaryText
                }
                maxLines = 1
                textAppearanceResource = R.style.TextAppearance_AppCompat_Subhead
            }.lparams {
                marginEnd = dip(16)
                bottomMargin = dip(12)
                bottomOf(R.id.substitution_text_upper)
                sameStart(R.id.substitution_text_upper)
            }

        }
    }
}
