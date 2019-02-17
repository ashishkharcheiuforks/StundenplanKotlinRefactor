package com.maxkrass.stundenplankotlinrefactor.manageteachers

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.toAndroidPair
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.ManageTeachersView
import com.maxkrass.stundenplankotlinrefactor.data.Teacher
import com.maxkrass.stundenplankotlinrefactor.extensions.email
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import com.maxkrass.stundenplankotlinrefactor.viewteacher.ViewTeacherActivity
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Max made this for Stundenplan2 on 10.07.2016.
 */

const val SUBSTITUTION_NOTIFICATION_ID = 0x0840
const val UPCOMING_LESSON_NOTIFICATION_ID = 0x0460
const val VIEW_TEACHER_REQUEST_CODE = 0x0806
private const val BUTTON_EDIT = 0
private const val BUTTON_DELETE = 1

class ManageTeachersFragment :
        MainActivityFragment<ManageTeachersPresenter, ManageTeachersView>(),
        ManageTeachersView,
        FirebaseTeacherAdapter.TeacherViewHolder.Host {

    override fun onTeacherEmailClicked(teacher: Teacher) {
        email(teacher.contraction + "@max-planck.com")
    }

    override fun providePresenter(): ManageTeachersPresenter = ManageTeachersPresenter()

    override fun onTeacherClicked(
            teacher: Teacher,
            viewHolder: FirebaseTeacherAdapter.TeacherViewHolder
    ) {

        val intent = Intent(activity, ViewTeacherActivity::class.java).apply {
            putExtra("position", viewHolder.adapterPosition)
            putExtra("teacher", teacher)
            putExtra("fontSize", viewHolder.itemView.teacherName.textSize)
            putExtra("padding", with(viewHolder.itemView.teacherName) {
                return@with Rect(paddingLeft, paddingTop, paddingRight, paddingBottom)
            })
            putExtra("color",
                    viewHolder.itemView.teacherName.currentTextColor)
        }

        val decorView = requireActivity().window.decorView
        val statusBackground = decorView.find<View>(android.R.id.statusBarBackground)

        val elements = mutableListOf(
                viewHolder.itemView.teacherName to "teacher_name",
                requireActivity().find<View>(R.id.main_app_bar_layout) to getString(R.string.main_app_bar_layout_transition_name),
                statusBackground to statusBackground.transitionName)
                .apply {
                    if (viewHolder.itemView.teacherEmail.visibility != View.GONE) {
                        add(viewHolder.itemView.teacherEmail to "teacher_email_icon")
                    }
                }.map { it.toAndroidPair() }

        val options1 = ActivityOptions.makeSceneTransitionAnimation(activity, *elements.toTypedArray())
        startActivityForResult(intent, VIEW_TEACHER_REQUEST_CODE, options1.toBundle())
    }

    /*private val mTeacherRecyclerView by lazy {
        view?.find<RecyclerView>(R.id.teachers_recycler_view)
    }

    private var targetTextSize: Float = 0.toFloat()
    private var targetTextColors: ColorStateList? = null
    private var selectedTeacherPosition: Int = 0

    fun onActivityReenter(sultCode: Int, data: Intent?) {

        if (data == null) {
            return
        }

        selectedTeacherPosition = data.getIntExtra("position", 0)

        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onSharedElementStart(sharedElementNames: List<String>?,
                                              sharedElements: List<View>?,
                                              sharedElementSnapshots: List<View>?) {
                val viewHolder = mTeacherRecyclerView?.findViewHolderForAdapterPosition(
                        selectedTeacherPosition) as FirebaseTeacherAdapter.TeacherViewHolder

                val teacherName = viewHolder.itemView.teacherName
                targetTextSize = teacherName.textSize
                targetTextColors = teacherName.textColors
                teacherName.setTextColor(data.getIntExtra("color", Color.BLACK))
                val textSize = data.getFloatExtra("fontSize", targetTextSize)
                teacherName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                val padding = data.getParcelableExtra<Rect>("padding")
                teacherName.setPadding(padding.left, padding.top, padding.right, padding.bottom)
            }

            override fun onSharedElementEnd(sharedElementNames: List<String>?,
                                            sharedElements: List<View>?,
                                            sharedElementSnapshots: List<View>?) {
                val viewHolder = mTeacherRecyclerView?.findViewHolderForAdapterPosition(
                        selectedTeacherPosition) as FirebaseTeacherAdapter.TeacherViewHolder
                val teacherName = viewHolder.itemView.teacherName
                teacherName.setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize)
                teacherName.setTextColor(targetTextColors)
            }
        })
    }*/

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return ManageTeachersFragmentUI().createView(AnkoContext.create(requireContext(), this))
    }

    interface OnTeacherChosenListener {
        fun onTeacherChosen(teacher: Teacher?)
    }
}
