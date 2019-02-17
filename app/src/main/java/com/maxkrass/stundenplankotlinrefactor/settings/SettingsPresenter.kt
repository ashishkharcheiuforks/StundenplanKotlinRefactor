package com.maxkrass.stundenplankotlinrefactor.settings

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.widget.CompoundButton
import androidx.core.content.edit
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.maxkrass.stundenplankotlinrefactor.commons.SettingsView
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionSubject
import com.maxkrass.stundenplankotlinrefactor.data.Uid
import com.maxkrass.stundenplankotlinrefactor.extensions.put
import com.maxkrass.stundenplankotlinrefactor.substitution.FirebaseSubstitutionSubjectAdapter
import net.grandcentrix.thirtyinch.TiPresenter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

/**
 * Max made this for StundenplanKotlinRefactor on 14.12.2017.
 */
class SettingsPresenter(
    context: Context,
    uid: Uid,
    override val kodein: Kodein
) : TiPresenter<SettingsView>(), KodeinAware, FirebaseSubstitutionSubjectAdapter.SubstitutionSubjectsViewHolder.Host {

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }
    private val firebaseMessaging: FirebaseMessaging by instance()

    private val mSubstitutionSubjectsRef: DatabaseReference by lazy {
        FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(uid)
                .child("substitutionSubjects")
    }

    fun subscribeToCheckPlan() {
        firebaseMessaging.subscribeToTopic(CHECK_PLAN)
    }

    fun unsubscribeFromCheckPlan() {
        firebaseMessaging.unsubscribeFromTopic(CHECK_PLAN)
    }

    fun createCheckedListener(category: String, func: (isChecked: Boolean) -> Unit = {}):
            (buttonView: CompoundButton?, isChecked: Boolean) -> Unit =
            { _, isChecked ->
                preferences.edit { put(category to isChecked) }
                func(isChecked)
            }

    val config: Map<String, Boolean>
        get() {
            with(preferences) {
                return mapOf(RECEIVE_SUBSTITUTION_NOTIFICATIONS to getBoolean(
                        RECEIVE_SUBSTITUTION_NOTIFICATIONS, true),
                             MY_SUBJECTS to getBoolean(MY_SUBJECTS, true),
                             EF to getBoolean(EF, true),
                             Q1 to getBoolean(Q1, true),
                             Q2 to getBoolean(Q2, true))
            }
        }
    val adapter = FirebaseSubstitutionSubjectAdapter(
            FirebaseRecyclerOptions.Builder<SubstitutionSubject>().setQuery(mSubstitutionSubjectsRef) { snapshot ->
                SubstitutionSubject(snapshot.getValue(String::class.java) ?: "", snapshot.key ?: "")
            }.build(),
            context,
            this)
}