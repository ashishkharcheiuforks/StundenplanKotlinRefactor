package com.maxkrass.stundenplankotlinrefactor.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import org.kodein.di.LazyKodein

/**
 * Max made this for Stundenplan on 05.02.2016.
 */

class SettingsFragment :
        MainActivityFragment<SettingsPresenter, com.maxkrass.stundenplankotlinrefactor.settings.View>(),
        LazyKodeinAware,
        com.maxkrass.stundenplankotlinrefactor.settings.View {

    override fun providePresenter(): SettingsPresenter = SettingsPresenter(ctx, uid, kodein)

    override val kodein: LazyKodein
        get() = LazyKodein(appKodein)
    override val showsTabs: Boolean
        get() = false
    override val toolbarTitle: String
        get() = getString(R.string.action_settings)

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return SettingsUI().createView(AnkoContext.create(ctx, this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        substitution_notification_switch.setOnCheckedChangeListener(
                presenter.createCheckedListener(RECEIVE_SUBSTITUTION_NOTIFICATIONS) {
                    if (it) presenter.subscribeToCheckPlan()
                    else presenter.unsubscribeFromCheckPlan()
                })
    }

    override fun onStart() {
        super.onStart()
        substitution_subjects_list.adapter = presenter.adapter
        val config = presenter.config
        substitution_notification_switch.isChecked = config[RECEIVE_SUBSTITUTION_NOTIFICATIONS] ?: true
        check_box_show_my_subjects.isChecked = config[MY_SUBJECTS] ?: true
        check_box_show_ef.isChecked = config[EF] ?: true
        check_box_show_q1.isChecked = config[Q1] ?: true
        check_box_show_q2.isChecked = config[Q2] ?: true
    }

}
