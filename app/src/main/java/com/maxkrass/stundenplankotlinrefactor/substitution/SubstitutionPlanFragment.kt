package com.maxkrass.stundenplankotlinrefactor.substitution

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.viewPager

class SubstitutionPlanFragment : MainActivityFragment<SubstitutionPlanPresenter, SubstitutionPlanView>(), SubstitutionPlanView {
    override val showsTabs: Boolean
        get() = true

    override fun providePresenter(): SubstitutionPlanPresenter {
        return SubstitutionPlanPresenter()
    }

    override val toolbarTitle: String
        get() = "Substitutin Plan"

    private val mainTabLayout by lazy { requireActivity().find<TabLayout>(R.id.main_tab_layout) }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = with(ctx) {
        super.onCreateView(inflater, container, savedInstanceState)
        val pager = viewPager {
            id = R.id.substitution_plan_view_pager
            adapter = SubstitutionPlanPagerAdapter(childFragmentManager,
                                                   uid,
                                                   mainTabLayout)
            offscreenPageLimit = 2
        }
        mainTabLayout.setupWithViewPager(pager)
        return pager
    }

}
