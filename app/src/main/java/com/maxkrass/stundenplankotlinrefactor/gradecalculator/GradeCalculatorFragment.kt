package com.maxkrass.stundenplankotlinrefactor.gradecalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.data.Subjects
import com.maxkrass.stundenplankotlinrefactor.extensions.inflate
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import kotlinx.android.synthetic.main.fragment_grade_calculator.*
import org.jetbrains.anko.textResource
import java.util.*

class GradeCalculatorFragment :
        MainActivityFragment<GradeCalculatorPresenter, GradeCalculatorContract.View>(),
        OnClickListener,
        GradeCalculatorContract.View {

    override fun providePresenter(): GradeCalculatorPresenter = GradeCalculatorPresenter(uid)

    override fun subjectsLoaded(subjects: Subjects) {
        mSubjects.clear()
        mSubjects.putAll(subjects)
        refreshUI()
    }

    private val mSubjects: Subjects = mutableMapOf()
    private val mSubjectList: TextView by lazy { fachListeTxt }
    private val grades = Stack<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_grade_calculator, container, false)
                ?: container?.inflate(R.layout.fragment_grade_calculator)
    }

    private fun refreshUI() {
        if (mSubjects.isEmpty()) {
            mSubjectList.textResource = R.string.no_saved_subject
        } else {
            setSubjectListText()
        }
    }

    private fun setSubjectListText() {
        mSubjectList.text = StringBuilder().apply {
            mSubjects.values.forEachIndexed { index, subject ->
                append(
                        when {
                           index < grades.size -> "${subject.name} (${grades[index]}), "
                           index < mSubjects.size - 1 -> "${subject.name}, "
                           else -> subject.name
                       }
                )
            }
        }.removeSuffix(", ")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bBack -> if (!grades.isEmpty()) {
                grades.pop()
            }
            R.id.bDelete -> grades.clear()
            else -> {
                val b = v as Button
                grades.push(b.text.toString().toInt())
            }
        }
        refreshUI()
    }
}
