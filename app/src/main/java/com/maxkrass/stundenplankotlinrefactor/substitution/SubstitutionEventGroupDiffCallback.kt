package com.maxkrass.stundenplankotlinrefactor.substitution

import androidx.recyclerview.widget.DiffUtil
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEventGroup

object SubstitutionEventGroupDiffCallback : DiffUtil.ItemCallback<SubstitutionEventGroup>() {
    override fun areItemsTheSame(oldItem: SubstitutionEventGroup, newItem: SubstitutionEventGroup): Boolean =
            oldItem.headerText == newItem.headerText

    override fun areContentsTheSame(oldItem: SubstitutionEventGroup, newItem: SubstitutionEventGroup): Boolean =
            oldItem.events == newItem.events
}