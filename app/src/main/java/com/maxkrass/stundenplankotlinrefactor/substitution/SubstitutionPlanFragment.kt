package com.maxkrass.stundenplankotlinrefactor.substitution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.SubstitutionPlanView
import com.maxkrass.stundenplankotlinrefactor.extensions.viewPager
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import org.jetbrains.anko.find

class SubstitutionPlanFragment :
        MainActivityFragment<SubstitutionPlanPresenter, SubstitutionPlanView>(),
        SubstitutionPlanView {

    override fun providePresenter() = SubstitutionPlanPresenter()

    private val mainTabLayout by lazy { requireActivity().find<TabLayout>(R.id.main_tab_layout) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = with(requireContext()) {
        super.onCreateView(inflater, container, savedInstanceState)
        return viewPager {
            id = R.id.substitution_plan_view_pager
            adapter = SubstitutionPlanPagerAdapter(this@with, childFragmentManager, mainTabLayout)
            offscreenPageLimit = 2
            mainTabLayout.setupWithViewPager(this)
        }
    }
}
