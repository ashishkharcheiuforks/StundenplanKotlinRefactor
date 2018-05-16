package com.maxkrass.stundenplankotlinrefactor.gradecalculator

import android.view.View
import android.widget.Button
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.extensions.Stack
import com.maxkrass.stundenplankotlinrefactor.extensions.bind
import com.maxkrass.stundenplankotlinrefactor.extensions.divider
import com.maxkrass.stundenplankotlinrefactor.extensions.startPadding
import org.jetbrains.anko.*

class GradeCalculatorUI(private val grades: Binder<Stack<Int>>) : AnkoComponent<GradeCalculatorFragment> {
    override fun createView(ui: AnkoContext<GradeCalculatorFragment>): View = with(ui) {

        verticalLayout {

            relativeLayout {

                textView("Punkte Ø: 0,00") {
                    id = R.id.punkteD
                    bind(grades) {
                        text = resources.getString(R.string.points_average, if (it.isEmpty()) 0.0 else it.average())
                    }
                }.lparams {
                    alignParentStart()
                    centerVertically()
                    marginStart = dip(10)
                }

                textView("Noten Ø: 0,00") {
                    id = R.id.notenD
                    bind(grades) {
                        text = resources.getString(R.string.grade_average, if (it.isEmpty()) 0.0 else (17.0 - it.average()) / 3.0)
                    }
                }.lparams {
                    centerVertically()
                    marginStart = dip(20)
                    endOf(R.id.punkteD)
                }

            }.lparams(width = matchParent, height = dip(50))

            divider { }

            tableLayout {
                padding = dip(7)
                isShrinkAllColumns = true
                isStretchAllColumns = true

                tableRow {
                    id = R.id.tableRow1

                    textView("Sehr Gut") {
                        id = R.id.textView1
                        maxLines = 1
                    }
                    button("15") {
                        id = R.id.b15
                    }
                    button("14") {
                        id = R.id.b14
                    }
                    button("13") {
                        id = R.id.b13
                    }
                }

                divider { }

                tableRow {
                    id = R.id.tableRow2

                    textView("Gut") {
                        id = R.id.textView2
                        maxLines = 1
                    }
                    button("12") {
                        id = R.id.b12
                    }
                    button("11") {
                        id = R.id.b11
                    }
                    button(R.string.ten) {
                        id = R.id.b10
                    }
                }

                divider { }

                tableRow {
                    id = R.id.tableRow3

                    textView("Befriedigend") {
                        id = R.id.textView3
                        maxLines = 1
                    }
                    button(R.string.nine) {
                        id = R.id.b9
                    }
                    button(R.string.eight) {
                        id = R.id.b8
                    }
                    button(R.string.seven) {
                        id = R.id.b7
                    }
                }

                divider { }

                tableRow {
                    id = R.id.tableRow4

                    textView("Ausreichend") {
                        id = R.id.textView4
                        maxLines = 1
                    }
                    button(R.string.six) {
                        id = R.id.b6
                    }
                    button(R.string.five) {
                        id = R.id.b5
                    }
                    button(R.string.four) {
                        id = R.id.b4
                    }
                }

                divider { }

                tableRow {
                    id = R.id.tableRow5

                    textView("Mangelhaft") {
                        id = R.id.textView5
                        maxLines = 1
                    }
                    button(R.string.three) {
                        id = R.id.b3
                    }
                    button(R.string.two) {
                        id = R.id.b2
                    }
                    button(R.string.one) {
                        id = R.id.b1
                    }
                }

                divider { }

                tableRow {
                    id = R.id.tableRow6

                    textView("Ungenügend") {
                        id = R.id.textView6
                        maxLines = 1
                    }
                    button("0") {
                        id = R.id.b0
                    }
                    button(R.string.grade_back) {
                        id = R.id.bBack
                    }
                    button("X") {
                        id = R.id.bDelete
                    }
                }
            }.lparams(width = matchParent, height = wrapContent)

            divider {}

            scrollView {
                id = R.id.sview331

                verticalLayout {

                    textView("[]") {
                        id = R.id.notenListe
                        bind(grades) {
                            text = it.toString()
                        }
                    }.lparams {
                        bottomMargin = dip(3)
                    }

                    divider {}

                    textView("Deine Fächer:") {
                        startPadding = dip(7)
                        topPadding = dip(4)
                    }

                    textView("") {
                        id = R.id.fachListeTxt
                        padding = dip(7)
                    }

                }.lparams(width = matchParent, height = wrapContent)

            }.lparams(width = matchParent, height = wrapContent) {
                topMargin = dip(2)
            }
        }.applyRecursively { view ->
            if (view is Button) {
                view.setOnClickListener(owner)
            }
        }
    }
}