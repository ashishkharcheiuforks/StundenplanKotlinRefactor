package com.maxkrass.stundenplankotlinrefactor.chooseteacher

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.recyclerview.widget.RecyclerView
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.ChooseTeacherView
import com.maxkrass.stundenplankotlinrefactor.data.Teacher
import com.maxkrass.stundenplankotlinrefactor.manageteachers.FirebaseTeacherAdapter
import net.grandcentrix.thirtyinch.TiFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class ChooseTeacherFragment : TiFragment<ChooseTeacherPresenter, ChooseTeacherView>(), ChooseTeacherView {

    override fun onTeacherChosen(teacher: Teacher) {
        mOnTeacherChosenListener?.onTeacherChosen(teacher)
    }

    override fun providePresenter(): ChooseTeacherPresenter = ChooseTeacherPresenter()

    private val mOnTeacherChosenListener: OnTeacherChosenListener? by lazy {
        try {
            return@lazy activity as OnTeacherChosenListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnTeacherChosenListener")
        }
    }
    private val mTeacherRecyclerView by lazy {
        view?.find<RecyclerView>(R.id.teachers_recycler_view)
    }

    private var targetTextSize: Float = 0.toFloat()
    private var targetTextColors: ColorStateList? = null
    private var selectedTeacherPosition: Int = 0

    fun onActivityReenter(resultCode: Int, data: Intent?) {

        if (data == null) {
            return
        }

        selectedTeacherPosition = data.getIntExtra("position", 0)

        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onSharedElementStart(
                sharedElementNames: List<String>?,
                sharedElements: List<View>?,
                sharedElementSnapshots: List<View>?
            ) {
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

            override fun onSharedElementEnd(
                sharedElementNames: List<String>?,
                sharedElements: List<View>?,
                sharedElementSnapshots: List<View>?
            ) {
                val viewHolder = mTeacherRecyclerView?.findViewHolderForAdapterPosition(
                        selectedTeacherPosition) as FirebaseTeacherAdapter.TeacherViewHolder
                val teacherName = viewHolder.itemView.teacherName
                teacherName.setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize)
                teacherName.setTextColor(targetTextColors)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return ChooseTeacherUi().createView(AnkoContext.create(requireContext(), this))
    }

    interface OnTeacherChosenListener {
        fun onTeacherChosen(teacher: Teacher?)
    }
}