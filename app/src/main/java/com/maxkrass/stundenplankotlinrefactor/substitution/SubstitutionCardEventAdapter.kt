package com.maxkrass.stundenplankotlinrefactor.substitution

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import splitties.typesaferecyclerview.ItemViewHolder

/**
 * Max made this for Stundenplan2 on 23.04.2017.
 */

internal class SubstitutionCardEventAdapter(
    private val host: SubstitutionEventViewHolder.Host,
    private val context: Context
) : ListAdapter<SubstitutionEvent, SubstitutionCardEventAdapter.SubstitutionEventViewHolder>(SubstitutionEventDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubstitutionEventViewHolder =
            SubstitutionEventViewHolder(host, SingleSubstitutionEventListItem(context))

    override fun onBindViewHolder(holder: SubstitutionEventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    internal class SubstitutionEventViewHolder(
        host: Host,
        view: SingleSubstitutionEventListItem
    ) :
            ItemViewHolder<SubstitutionEvent,
                    SingleSubstitutionEventListItem,
                    SubstitutionEventViewHolder.Host>(host, view) {

        override fun SingleSubstitutionEventListItem.onBind() {
            itemView.data = data
        }

        interface Host
    }
}
