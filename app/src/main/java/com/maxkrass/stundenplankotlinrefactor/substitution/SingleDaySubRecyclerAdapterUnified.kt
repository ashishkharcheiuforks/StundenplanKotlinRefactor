package com.maxkrass.stundenplankotlinrefactor.substitution

import android.content.Context
import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.maxkrass.stundenplankotlinrefactor.data.Grade
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEventGroup
import com.maxkrass.stundenplankotlinrefactor.extensions.__ValueEventListener
import com.maxkrass.stundenplankotlinrefactor.extensions.addSingleEventListener
import com.maxkrass.stundenplankotlinrefactor.extensions.getTypedValue
import com.maxkrass.stundenplankotlinrefactor.settings.EF
import com.maxkrass.stundenplankotlinrefactor.settings.MY_SUBJECTS
import com.maxkrass.stundenplankotlinrefactor.settings.Q1
import com.maxkrass.stundenplankotlinrefactor.settings.Q2
import splitties.typesaferecyclerview.ItemViewHolder

/**
 * The adapter for a single substitution tab. It features section headers.
 */
class SingleDaySubRecyclerAdapterUnified(private val host: CardViewItemViewHolder.Host,
                                         private val mContext: Context,
                                         private val mListener: OnLoadingFinishedListener,
                                         private val mClickListener: OnSubstitutionItemClickListener) :
        RecyclerView.Adapter<SingleDaySubRecyclerAdapterUnified.CardViewItemViewHolder>() {
    private val mEventGroups: MutableList<SubstitutionEventGroup> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewItemViewHolder =
            CardViewItemViewHolder(host, SubstitutionCardItemList(mContext))

    override fun onBindViewHolder(holder: CardViewItemViewHolder, position: Int) {
        holder.bind(mEventGroups[position])
    }

    override fun getItemCount(): Int = mEventGroups.size

    fun setNewContent(list: List<SubstitutionEvent>) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(mContext)
        mEventGroups.clear()
        val listener: __ValueEventListener.() -> Unit = {
            onDataChange { snapshot ->
                val mySavedSubjects = snapshot.getTypedValue<HashMap<String, String>>() ?: emptyMap<String, String>()
                // TODO: Finish new loading routine
                val mySubjects = SubstitutionEventGroup("Meine Fächer")
                val efSubs = SubstitutionEventGroup("EF")
                val q1Subs = SubstitutionEventGroup("Q1")
                val q2Subs = SubstitutionEventGroup("Q2")
                list.forEach { event ->
                    if (mySavedSubjects.isNotEmpty() && mySavedSubjects[event.subject] == event.gradeText) {
                        mySubjects.events.add(event)
                    } else {
                        when (event.grade) {
                            Grade.EF   -> efSubs.events.add(event)
                            Grade.Q1   -> q1Subs.events.add(event)
                            Grade.Q2   -> q2Subs.events.add(event)
                            Grade.LR   -> TODO()
                            Grade.SL   -> TODO()
                            Grade.None -> TODO()
                        }
                    }
                }
                if (preferences.getBoolean(MY_SUBJECTS, true) && mySubjects.events.isNotEmpty())
                    mEventGroups.add(mySubjects)
                if (preferences.getBoolean(EF, true) && efSubs.events.isNotEmpty())
                    mEventGroups.add(efSubs)
                if (preferences.getBoolean(Q1, true) && q1Subs.events.isNotEmpty())
                    mEventGroups.add(q1Subs)
                if (preferences.getBoolean(Q2, true) && q2Subs.events.isNotEmpty())
                    mEventGroups.add(q2Subs)
                if (mEventGroups.isEmpty())
                    mEventGroups.add(SubstitutionEventGroup("keine Vertretungen"))
                notifyDataSetChanged()
                mListener.onLoadingFinished()
            }
            onCancelled {
                mListener.onLoadingFinished()
            }
        }
        FirebaseDatabase
                .getInstance()
                .reference
                .child("substitutionSubjects")
                .child(FirebaseAuth.getInstance().currentUser?.uid)
                .addSingleEventListener(listener)
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
                                                                             view.context,
                                                                             data.events)
        }

        interface Host

    }
}