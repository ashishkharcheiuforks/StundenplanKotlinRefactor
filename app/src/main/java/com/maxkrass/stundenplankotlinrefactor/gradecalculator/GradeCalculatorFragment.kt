package com.maxkrass.stundenplankotlinrefactor.gradecalculator


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.data.Subjects
import com.maxkrass.stundenplankotlinrefactor.extensions.Stack
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find


class GradeCalculatorFragment : MainActivityFragment<GradeCalculatorPresenter, GradeCalculatorContract.View>(), OnClickListener, GradeCalculatorContract.View {
    override val toolbarTitle: String
        get() = "Grade calculator"
    override val showsTabs: Boolean
        get() = false

    override fun providePresenter(): GradeCalculatorPresenter = GradeCalculatorPresenter(uid)

    override fun subjectsLoaded(subjects: Subjects) {
        mSubjects.clear()
        mSubjects.putAll(subjects)
        refreshUI()
    }

    private val mSubjects = Subjects()
    private val mSubjectList: TextView by lazy { find<TextView>(R.id.fachListeTxt) }
    private val grades = Stack<Int>()
    private val binder = Binder(Stack<Int>(), false)


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return GradeCalculatorUI(binder).createView(AnkoContext.create(ctx, this))
    }

    private fun refreshUI() {
        binder.item = grades
        if (mSubjects.isEmpty()) {
            mSubjectList.setText(R.string.no_saved_subject)
        } else {
            setSubjectListText()
        }
    }

    private fun setSubjectListText() {
        mSubjectList.text = StringBuilder().apply {
            var i = 0
            mSubjects.forEach { (_, value) ->
                append(when {
                           i < grades.size        -> "${value.name} (${grades[i]}), "
                           i < mSubjects.size - 1 -> "${value.name}, "
                           else                   -> value.name
                       })
                i++
            }

        }.removeSuffix(", ")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bBack   -> if (!grades.isEmpty()) {
                grades.pop()
            }
            R.id.bDelete -> grades.clear()
            else         -> {
                val b = v as Button
                grades.push(b.text.toString().toInt())
            }
        }
        refreshUI()
    }
}