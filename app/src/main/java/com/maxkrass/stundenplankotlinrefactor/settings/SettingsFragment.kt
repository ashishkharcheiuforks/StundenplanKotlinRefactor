package com.maxkrass.stundenplankotlinrefactor.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

/**
 * Max made this for Stundenplan on 05.02.2016.
 */

class SettingsFragment :
        MainActivityFragment<SettingsPresenter, com.maxkrass.stundenplankotlinrefactor.commons.SettingsView>(),
        KodeinAware,
        com.maxkrass.stundenplankotlinrefactor.commons.SettingsView {

    override fun providePresenter(): SettingsPresenter = SettingsPresenter(requireContext(), uid, kodein)

    override val kodein by kodein()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return SettingsUI().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        substitution_notification_switch.onCheckedChange { _, _ ->
            checkedChangeListener
        }

    }

    private val checkedChangeListener = presenter.createCheckedListener(RECEIVE_SUBSTITUTION_NOTIFICATIONS) {
        if (it) presenter.subscribeToCheckPlan()
        else presenter.unsubscribeFromCheckPlan()
    }

    override fun onStart() {
        super.onStart()
        substitution_subjects_list.adapter = presenter.adapter
        val config = presenter.config
        substitution_notification_switch.isChecked = config[RECEIVE_SUBSTITUTION_NOTIFICATIONS]
                ?: true
        check_box_show_my_subjects.isChecked = config[MY_SUBJECTS] ?: true
        check_box_show_ef.isChecked = config[EF] ?: true
        check_box_show_q1.isChecked = config[Q1] ?: true
        check_box_show_q2.isChecked = config[Q2] ?: true
    }
}
