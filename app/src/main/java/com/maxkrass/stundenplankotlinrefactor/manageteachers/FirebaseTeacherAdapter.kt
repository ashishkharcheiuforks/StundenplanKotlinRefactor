package com.maxkrass.stundenplankotlinrefactor.manageteachers

import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.data.Teacher
import com.maxkrass.stundenplankotlinrefactor.extensions.SingleLineTeacherListItem
import org.jetbrains.anko.sdk25.coroutines.onClick
import splitties.typesaferecyclerview.ItemViewHolder

/**
 * Max made this for Stundenplan2 on 19.07.2016.
 */
class FirebaseTeacherAdapter(
        options: FirebaseRecyclerOptions<Teacher>,
        private val host: TeacherViewHolder.Host) :
        FirebaseRecyclerAdapter<Teacher, FirebaseTeacherAdapter.TeacherViewHolder>(options) {

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int, model: Teacher) {
        holder.bind(model)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder =
            TeacherViewHolder(host, parent)

    class TeacherViewHolder(host: FirebaseTeacherAdapter.TeacherViewHolder.Host,
                            parent: ViewGroup) :
            ItemViewHolder<Teacher, SingleLineTeacherListItem, FirebaseTeacherAdapter.TeacherViewHolder.Host>(
                    host,
                    R.layout.teacher_view,
                    parent) {

        override fun SingleLineTeacherListItem.onBind() {
            teacherName.text = data.name
            teacherEmail.onClick { itemEmailClickListener }
            setOnClickListener(itemClickListener)
        }

        private val itemClickListener = View.OnClickListener {
            host.onTeacherClicked(data, this)
        }

        private val itemEmailClickListener = View.OnClickListener {
            host.onTeacherEmailClicked(data)
        }

        interface Host {
            fun onTeacherClicked(teacher: Teacher, viewHolder: TeacherViewHolder)
            fun onTeacherEmailClicked(teacher: Teacher)
        }
    }

}
