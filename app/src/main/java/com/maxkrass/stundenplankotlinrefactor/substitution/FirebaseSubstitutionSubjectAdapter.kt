package com.maxkrass.stundenplankotlinrefactor.substitution

import android.content.Context
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionSubject
import splitties.typesaferecyclerview.ItemViewHolder

/**
 * Max Krass made this for Stundenplan2 on 22.09.2016.
 */

class FirebaseSubstitutionSubjectAdapter(options: FirebaseRecyclerOptions<SubstitutionSubject>, private val context: Context, private val host: SubstitutionSubjectsViewHolder.Host) : FirebaseRecyclerAdapter<SubstitutionSubject, FirebaseSubstitutionSubjectAdapter.SubstitutionSubjectsViewHolder>(
        options) {

    override fun onBindViewHolder(holder: SubstitutionSubjectsViewHolder,
                                  position: Int,
                                  model: SubstitutionSubject) {

    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SubstitutionSubjectsViewHolder? {
        return SubstitutionSubjectsViewHolder(host, SubstitutionSubjectListItem(context))
    }

    class SubstitutionSubjectsViewHolder(host: Host,
                                         itemView: SubstitutionSubjectListItem) : ItemViewHolder<SubstitutionSubject, SubstitutionSubjectListItem, SubstitutionSubjectsViewHolder.Host>(
            host,
            itemView) {
        override fun SubstitutionSubjectListItem.onBind() {
            itemView.data = data
        }

        interface Host
    }

}
