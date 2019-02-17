package com.maxkrass.stundenplankotlinrefactor.substitution

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEventGroup
import splitties.typesaferecyclerview.ItemViewHolder

/**
 * The adapter for a single substitution tab. It features section headers.
 */
class SingleDaySubRecyclerAdapterUnified(
    private val host: CardViewItemViewHolder.Host,
    private val mContext: Context,
    private val mListener: OnLoadingFinishedListener,
    private val mClickListener: OnSubstitutionItemClickListener
) :
        ListAdapter<SubstitutionEventGroup,
                SingleDaySubRecyclerAdapterUnified.CardViewItemViewHolder>(SubstitutionEventGroupDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewItemViewHolder =
            CardViewItemViewHolder(host, SubstitutionCardItemList(mContext))

    override fun onBindViewHolder(holder: CardViewItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<SubstitutionEventGroup>?) {
        mListener.onLoadingFinished()
        super.submitList(list)
    }

    interface OnSubstitutionItemClickListener {
        fun onItemClick(event: SubstitutionEvent)
    }

    interface OnLoadingFinishedListener {
        fun onLoadingFinished()
    }

    class CardViewItemViewHolder(host: Host, private val view: SubstitutionCardItemList) :
            ItemViewHolder<SubstitutionEventGroup,
                    SubstitutionCardItemList,
                    CardViewItemViewHolder.Host>(host, view) {

        override fun SubstitutionCardItemList.onBind() {
            itemView.header.text = data.headerText
            itemView.substitutionList.adapter = SubstitutionCardEventAdapter(this,
                    view.context).apply {
                submitList(data.events)
            }
        }

        interface Host
    }
}
