package com.maxkrass.stundenplankotlinrefactor.substitution

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import splitties.typesaferecyclerview.ItemViewHolder

/**
 * Max made this for Stundenplan2 on 23.04.2017.
 */

internal class SubstitutionCardEventAdapter(
        private val host: SubstitutionEventViewHolder.Host,
        private val context: Context,
        private val mEvents: List<SubstitutionEvent>
) : RecyclerView.Adapter<SubstitutionCardEventAdapter.SubstitutionEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubstitutionEventViewHolder =
            SubstitutionEventViewHolder(host, SingleSubstitutionEventListItem(context))

    override fun onBindViewHolder(holder: SubstitutionEventViewHolder, position: Int) {
        holder.bind(mEvents[position])
    }

    override fun getItemCount(): Int {
        return mEvents.size
    }

    internal class SubstitutionEventViewHolder(host: Host,
                                               view: SingleSubstitutionEventListItem) :
            ItemViewHolder<SubstitutionEvent,
                    SingleSubstitutionEventListItem,
                    SubstitutionEventViewHolder.Host>(host, view) {

        override fun SingleSubstitutionEventListItem.onBind() {
            itemView.data = data
        }

        interface Host

    }
}
