package com.maxkrass.stundenplankotlinrefactor.manageteachers

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Pair
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.recyclerview.widget.RecyclerView
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.ManageTeachersView
import com.maxkrass.stundenplankotlinrefactor.data.Teacher
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import com.maxkrass.stundenplankotlinrefactor.viewteacher.ViewTeacherActivity
import kotlinx.android.synthetic.main.fragment_manage_teachers.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.email
import org.jetbrains.anko.support.v4.startActivityForResult
import java.util.*

/**
 * Max made this for Stundenplan2 on 10.07.2016.
 */

const val SUBSTITUTION_NOTIFICATION_ID = 0x0840
const val UPCOMING_LESSON_NOTIFICATION_ID = 0x0460
const val VIEW_TEACHER_REQUEST_CODE = 0x0806
private const val BUTTON_EDIT = 0
private const val BUTTON_DELETE = 1

class ManageTeachersFragment : MainActivityFragment<ManageTeachersPresenter, ManageTeachersView>(), ManageTeachersView, FirebaseTeacherAdapter.TeacherViewHolder.Host {
    override fun onTeacherEmailClicked(teacher: Teacher) {
        email(teacher.contraction + "@max-planck.com")
    }

    override val showsTabs: Boolean get() = false
    override val toolbarTitle: String by lazy { getString(R.string.manage_teachers_title) }

    override fun providePresenter(): ManageTeachersPresenter = ManageTeachersPresenter()

    override fun onTeacherClicked(teacher: Teacher,
                                  viewHolder: FirebaseTeacherAdapter.TeacherViewHolder) {
        if (!isInSelectMode) {
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
            val elements = ArrayList<Pair<View, String>>()
            elements.add(Pair(viewHolder.itemView.teacherName, "teacher_name"))
            elements.add(Pair(act.findViewById(R.id.main_app_bar_layout),
                              getString(R.string.main_app_bar_layout_transition_name)))
            if (viewHolder.itemView.teacherEmail.visibility != View.GONE) {
                elements.add(Pair(viewHolder.itemView.teacherEmail, "teacher_email_icon"))
            }
            val decorView = act.window.decorView
            val statusBackground = decorView.findViewById<View>(android.R.id.statusBarBackground)
            //View navBackground = decorView.findViewById(android.R.id.navigationBarBackground);
            val statusPair = Pair.create(statusBackground,
                                         statusBackground.transitionName)
            //Pair<View, String> navPair = Pair.create(navBackground, navBackground.getTransitionName());
            elements.add(statusPair)
            //elements.add(navPair);
            val options = ActivityOptions.makeSceneTransitionAnimation(activity,
                                                                       *elements.toTypedArray())
            startActivityForResult(intent,
                                   VIEW_TEACHER_REQUEST_CODE,
                                   options.toBundle())
            startActivityForResult<ViewTeacherActivity>(VIEW_TEACHER_REQUEST_CODE)
        } else {
            mOnTeacherChosenListener?.onTeacherChosen(teacher)
        }
    }

    private val mOnTeacherChosenListener: OnTeacherChosenListener? by lazy {
        if (isInSelectMode) {
            try {
                return@lazy activity as OnTeacherChosenListener
            } catch (e: ClassCastException) {
                throw ClassCastException(activity.toString() + " must implement OnTeacherChosenListener")
            }

        }
        return@lazy null
    }
    private var mTeacherRecyclerView: RecyclerView = teachers_recycler_view

    private var targetTextSize: Float = 0.toFloat()
    private var targetTextColors: ColorStateList? = null
    private var selectedTeacherPosition: Int = 0

    private val isInSelectMode: Boolean
        get() = activity is OnTeacherChosenListener

    fun onActivityReenter(resultCode: Int, data: Intent?) {

        if (data == null) {
            return
        }

        selectedTeacherPosition = data.getIntExtra("position", 0)

        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onSharedElementStart(sharedElementNames: List<String>?,
                                              sharedElements: List<View>?,
                                              sharedElementSnapshots: List<View>?) {
                val viewHolder = mTeacherRecyclerView.findViewHolderForAdapterPosition(
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
                val viewHolder = mTeacherRecyclerView.findViewHolderForAdapterPosition(
                        selectedTeacherPosition) as FirebaseTeacherAdapter.TeacherViewHolder
                val teacherName = viewHolder.itemView.teacherName
                teacherName.setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize)
                teacherName.setTextColor(targetTextColors)
            }
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return ManageTeachersFragmentUI().createView(AnkoContext.create(ctx, this))
    }

    interface OnTeacherChosenListener {
        fun onTeacherChosen(teacher: Teacher?)
    }
}
