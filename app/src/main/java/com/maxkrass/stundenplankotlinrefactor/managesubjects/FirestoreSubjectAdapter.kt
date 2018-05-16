package com.maxkrass.stundenplankotlinrefactor.managesubjects

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.extensions.SingleLineSubjectListItem
import splitties.typesaferecyclerview.ItemViewHolder


/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */
class FirestoreSubjectAdapter(
        options: FirestoreRecyclerOptions<Subject>,
        private val host: SubjectViewHolder.Host) :
        FirestoreRecyclerAdapter<Subject, FirestoreSubjectAdapter.SubjectViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder =
            SubjectViewHolder(host, parent)

    override fun onBindViewHolder(viewHolder: SubjectViewHolder, position: Int, model: Subject) {
        viewHolder.bind(model)
    }

    class SubjectViewHolder(host: Host, parent: ViewGroup) :
            ItemViewHolder<Subject, SingleLineSubjectListItem, SubjectViewHolder.Host>(
                    host,
                    R.layout.subject_view,
                    parent) {

        override fun SingleLineSubjectListItem.onBind() {
            (subjectColor.background as GradientDrawable).setColor(data.colorInt)
            subjectName.text = data.name
            setOnClickListener(itemClickListener)
            setOnLongClickListener(itemLongClickListener)
        }

        private val itemClickListener = View.OnClickListener {
            host.onSubjectClicked(data)
        }

        private val itemLongClickListener = View.OnLongClickListener {
            host.onSubjectLongClicked(data)
        }

        interface Host {
            fun onSubjectClicked(subject: Subject)
            fun onSubjectLongClicked(subject: Subject): Boolean
        }

    }

}
