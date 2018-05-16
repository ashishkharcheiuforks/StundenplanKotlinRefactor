package com.maxkrass.stundenplankotlinrefactor.substitution

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter

/**
 * Max made this for Stundenplan2 on 16.07.2016.
 */

class SubstitutionPlanPagerAdapter(fm: FragmentManager,
                                   uId: String,
                                   val tabLayout: TabLayout) : FragmentPagerAdapter(fm) {

    private val fragment1: SingleDaySubstitutionFragment = SingleDaySubstitutionFragment.newInstance(1, uId, this)
    private val fragment2: SingleDaySubstitutionFragment = SingleDaySubstitutionFragment.newInstance(2, uId, this)
    private val fragment3: SingleDaySubstitutionFragment = SingleDaySubstitutionFragment.newInstance(3, uId, this)

    override fun getItem(position: Int): Fragment? = when (position) {
        0    -> fragment1
        1    -> fragment2
        2    -> fragment3
        else -> null
    }

    override fun getCount(): Int = 1

    override fun getItemPosition(item: Any): Int = PagerAdapter.POSITION_UNCHANGED

    override fun getPageTitle(position: Int): String = when (position) {
        0    -> fragment1.title
        1    -> fragment2.title
        2    -> fragment3.title
        else -> ""
    }

}
