package com.maxkrass.stundenplankotlinrefactor.gradecalculator

import com.maxkrass.stundenplankotlinrefactor.data.Subjects
import net.grandcentrix.thirtyinch.TiView

/**
 * Max made this for StundenplanKotlinRefactor on 10.12.2017.
 */

object GradeCalculatorContract {

    interface View : TiView {

        fun subjectsLoaded(subjects: Subjects)

    }

}