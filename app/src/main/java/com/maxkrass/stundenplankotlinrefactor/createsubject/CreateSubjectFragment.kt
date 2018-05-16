package com.maxkrass.stundenplankotlinrefactor.createsubject

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.commons.CreateSubjectView
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import com.maxkrass.stundenplankotlinrefactor.data.Teacher
import com.thetechnocafe.gurleensethi.liteutils.validator
import kotlinx.android.synthetic.main.fragment_create_subject.*
import net.grandcentrix.thirtyinch.TiFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.ctx


/**
 * Max made this for StundenplanKotlinRefactor on 06.10.2017.
 */
class CreateSubjectFragment : TiFragment<CreateSubjectPresenter<CreateSubjectView>, CreateSubjectView>(), View.OnClickListener, CreateSubjectView {
    override fun providePresenter(): CreateSubjectPresenter<CreateSubjectView> =
            CreateSubjectPresenter(SubjectRepository(arguments["uid"] as String),
                                   arguments["subjectKey"] as? String)

    private val binder = Binder(Subject())
    private var subject: Subject = Subject()
    private var adapter: ListAdapter? = null

    internal var mOnChooseListener: OnChooseListener? = null

    override fun nameInvalid() {
        (subject_name.parent.parent as TextInputLayout).error = getString(R.string.subject_name_invalid)
    }

    override fun subjectAlreadyExists() {
        (subject_name.parent.parent as TextInputLayout).error = "You already created this subject"
    }

    override fun abbreviationInvalid() {
        (subject_abbr.parent.parent as TextInputLayout).error = "I need an abbreviation"
    }

    override fun removeErrors() {
        (subject_name.parent.parent as TextInputLayout).error = ""
        (subject_abbr.parent.parent as TextInputLayout).error = ""
    }

    override fun savingFailed() {
        view?.let { snackbar(it, "Saving Failed!") }
    }

    override fun exitCreateDialog() {
        activity.finish()
    }

    override fun showSubject(subject: Subject) {
        this.subject = subject
        setSubject(subject)
    }

    override fun onAttach(activity: Context) {
        super.onAttach(activity)

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mOnChooseListener = activity as OnChooseListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnChooseListener")
        }

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = CreateSubjectUI(binder).createView(AnkoContext.Companion.create(ctx,
                                                                                   this))
        subject.color = "#F44336"
        setSubject(subject)

        adapter = object : ArrayAdapter<String>(activity,
                                                R.layout.color_row,
                                                resources.getStringArray(R.array.colors)) {

            internal val colorNames = resources.getStringArray(R.array.colorNames)

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val convertView = convertView
                /*val colorRowBinding: ColorRowBinding

                if (convertView == null) {
                    colorRowBinding = ColorRowBinding.inflate(activity.layoutInflater, parent, false)
                    convertView = colorRowBinding.getRoot()
                } else {
                    colorRowBinding = DataBindingUtil.findBinding(convertView)
                }

                (colorRowBinding.color_icon.getBackground() as GradientDrawable).setColor(android.graphics.Color.parseColor(
                        getItem(position)))
                colorRowBinding.name.setText(colorNames[position])*/

                return convertView!!
            }
        }

        return view
    }

    private fun setSubject(subject: Subject) {
        binder.item = subject
    }

    fun onTeacherChosen(teacher: Teacher?) {
        teacher?.let {
            subject.teacher = it.contraction
        }
    }

    internal fun selectColor() {
        AlertDialog.Builder(activity).setAdapter(
                adapter,
                { _, which ->
                    val newColor = resources.getStringArray(R.array.colors)[which]
                    (color_icon.background as GradientDrawable).setColor(Color.parseColor(newColor))
                    color_name_label.text = resources.getStringArray(R.array.colorNames)[which]
                    mOnChooseListener?.onShowChosenColor(subject.color, newColor)
                    subject.color = newColor
                }
        ).show()
    }

    interface OnChooseListener {
        fun onShowChosenColor(fromColor: String, toColor: String)

        fun onRequestChooseTeacher()
    }

    override fun onClick(v: View) {
            presenter.validateSubject(subject_name.validator(),
                                      subject_abbr.validator(),
                                      binder.item.color,
                                      binder.item.teacher)
    }
}