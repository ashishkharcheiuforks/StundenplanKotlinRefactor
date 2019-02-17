package com.maxkrass.stundenplankotlinrefactor.createsubject

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.textfield.TextInputLayout
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.commons.CreateSubjectView
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Teacher
import com.maxkrass.stundenplankotlinrefactor.extensions.inflate
import com.maxkrass.stundenplankotlinrefactor.extensions.longSnackbar
import com.maxkrass.stundenplankotlinrefactor.utils.validator
import kotlinx.android.synthetic.main.fragment_create_subject.*
import net.grandcentrix.thirtyinch.TiFragment
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * Max made this for StundenplanKotlinRefactor on 06.10.2017.
 */
@Suppress("TooManyFunctions")
class CreateSubjectFragment : TiFragment<CreateSubjectPresenter<CreateSubjectView>,
        CreateSubjectView>(), CreateSubjectView, LifecycleOwner {

    override fun providePresenter(): CreateSubjectPresenter<CreateSubjectView> =
            CreateSubjectPresenter(SubjectRepository(),
                    arguments?.get("subjectKey") as? String)

    private val binder = Binder(Subject())
    private val colorListAdapter: ListAdapter by lazy {
        object : ArrayAdapter<String>(
                requireContext(),
                R.layout.color_row,
                resources.getStringArray(R.array.colors)
        ) {
            val colorNames = resources.getStringArray(R.array.colorNames)

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                return convertView
                        ?: layoutInflater.inflate(R.layout.color_row, parent, false).apply {
                            (find<View>(R.id.color_icon).background as GradientDrawable).setStroke(dip(4), Color.parseColor(getItem(position)))
                            find<TextView>(R.id.name).text = colorNames[position]
                        }
            }
        }
    }

    private val teacherChosenObserver = Observer<Teacher?> {
        presenter.teacherChosen(it)
    }

    private val saveSubjectObserver = Observer<View> {
        presenter.validateSubject(subject_name.validator(),
                subject_abbr.validator(),
                binder.item.color,
                binder.item.teacher)
    }

    override fun onStart() {
        super.onStart()
        CreateSubjectActivity.teacherChosenEvent.observe(this, teacherChosenObserver)
        CreateSubjectActivity.mainActionClickEvent.observe(this, saveSubjectObserver)
    }

    override fun onStop() {
        super.onStop()
        CreateSubjectActivity.teacherChosenEvent.removeObserver(teacherChosenObserver)
        CreateSubjectActivity.mainActionClickEvent.removeObserver(saveSubjectObserver)
    }

    private var subjectNameError: CharSequence?
        get() = (subject_name.parent.parent as TextInputLayout).error
        set(value) {
            (subject_name.parent.parent as TextInputLayout).error = value
        }

    private var subjectAbbrError: CharSequence?
        get() = (subject_abbr.parent.parent as TextInputLayout).error
        set(value) {
            (subject_abbr.parent.parent as TextInputLayout).error = value
        }

    override fun nameInvalid() {
        subjectNameError = getString(R.string.subject_name_invalid)
    }

    override fun subjectAlreadyExists() {
        subjectNameError = getString(R.string.subject_name_already_exists)
    }

    override fun abbreviationInvalid() {
        subjectAbbrError = "I need an abbreviation"
    }

    override fun removeErrors() {
        subjectNameError = ""
        subjectAbbrError = ""
    }

    override fun savingFailed() {
        view?.longSnackbar("Saving Failed!")
    }

    override fun exitCreateDialog() {
        activity?.finish()
    }

    override fun showSubject(subject: Subject) {
        binder.item = subject
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        select_teacher.onClick {
            NavHostFragment.findNavController(this@CreateSubjectFragment).navigate(R.id.action_choose_teacher)
        }

        choose_color.onClick {
            selectColor()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_create_subject, container, false)
                ?: container?.inflate(R.layout.fragment_create_subject)
    }

    private fun selectColor() {
        AlertDialog.Builder(requireContext()).setAdapter(colorListAdapter) { _, which ->
            val newColor = resources.getStringArray(R.array.colors)[which]
            (color_icon.background as GradientDrawable).setColor(Color.parseColor(newColor))
            color_name_label.text = resources.getStringArray(R.array.colorNames)[which]
        }.show()
    }

}
