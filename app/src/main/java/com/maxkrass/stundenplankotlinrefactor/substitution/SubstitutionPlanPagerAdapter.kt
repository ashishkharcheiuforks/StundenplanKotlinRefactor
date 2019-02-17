package com.maxkrass.stundenplankotlinrefactor.substitution

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.factory

/**
 * Max made this for Stundenplan2 on 16.07.2016.
 */

class SubstitutionPlanPagerAdapter(
        context: Context,
        fm: FragmentManager,
        val tabLayout: TabLayout
) : FragmentPagerAdapter(fm), KodeinAware {

    override val kodein by kodein(context)

    private val fragmentFactory: (Int) -> SingleDaySubstitutionFragment by factory("sdsf")

    override fun getItem(position: Int): Fragment = fragmentFactory(position)

    override fun getCount(): Int = 3

    override fun getItemPosition(item: Any): Int = androidx.viewpager.widget.PagerAdapter.POSITION_UNCHANGED

    override fun getPageTitle(position: Int): String = fragmentFactory(position).title
}
