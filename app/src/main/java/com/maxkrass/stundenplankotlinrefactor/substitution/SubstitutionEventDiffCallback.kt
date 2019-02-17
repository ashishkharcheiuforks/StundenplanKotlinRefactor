package com.maxkrass.stundenplankotlinrefactor.substitution

import androidx.recyclerview.widget.DiffUtil
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent

object SubstitutionEventDiffCallback : DiffUtil.ItemCallback<SubstitutionEvent>() {
    override fun areItemsTheSame(oldItem: SubstitutionEvent, newItem: SubstitutionEvent): Boolean =
            oldItem.period == newItem.period && oldItem.grade == newItem.grade && oldItem.subject == newItem.subject

    override fun areContentsTheSame(oldItem: SubstitutionEvent, newItem: SubstitutionEvent): Boolean =
            oldItem == newItem
}