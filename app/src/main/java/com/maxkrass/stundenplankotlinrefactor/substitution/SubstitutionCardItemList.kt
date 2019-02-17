package com.maxkrass.stundenplankotlinrefactor.substitution

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maxkrass.stundenplankotlinrefactor.R
import org.jetbrains.anko.*

/**
 * Max made this for StundenplanKotlinRefactor on 22.10.2017.
 */
class SubstitutionCardItemList(context: Context) : androidx.cardview.widget.CardView(context), SubstitutionCardEventAdapter.SubstitutionEventViewHolder.Host {

    internal val header: TextView
    internal val substitutionList: RecyclerView

    init {
        addView(SubstitutionCard().createView(AnkoContext.create(context, this)), matchParent, wrapContent)
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.material_white))
        header = find(R.id.substitution_card_header)
        substitutionList = find(R.id.substitution_card_recycler_view)
        radius = dip(4).toFloat()
        layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            marginStart = dip(8)
            marginEnd = dip(8)
            topMargin = dip(4)
            bottomMargin = dip(4)
        }
    }
}
