package com.maxkrass.stundenplankotlinrefactor.main

import com.maxkrass.stundenplankotlinrefactor.gradecalculator.GradeCalculatorFragment
import com.maxkrass.stundenplankotlinrefactor.managesubjects.ManageSubjectsFragment
import com.maxkrass.stundenplankotlinrefactor.manageteachers.ManageTeachersFragment
import com.maxkrass.stundenplankotlinrefactor.settings.SettingsFragment
import com.maxkrass.stundenplankotlinrefactor.stundenplan.StundenplanFragment
import com.maxkrass.stundenplankotlinrefactor.substitution.SubstitutionPlanFragment

/**
 * Max made this for StundenplanKotlinRefactor on 05.09.2017.
 */
object MainActivityFragmentFactory {

    fun byTag(tag: String): MainActivityFragment<*, *> = when (tag) {
        MAIN_FRAGMENT_TAG     -> StundenplanFragment()
        SUBSTITUTION_PLAN_TAG -> SubstitutionPlanFragment()
        MANAGE_TEACHERS_TAG   -> ManageTeachersFragment()
        MANAGE_SUBJECTS_TAG   -> ManageSubjectsFragment()
        GRADE_CALCULATOR_TAG  -> GradeCalculatorFragment()
        SETTINGS_FRAGMENT_TAG -> SettingsFragment()
        else                  -> MainActivityFragment.createNullFragment()
    }

}
