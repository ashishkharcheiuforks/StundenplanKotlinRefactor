package com.maxkrass.stundenplankotlinrefactor.viewsubject

import android.os.Bundle
import android.view.MenuItem
import com.maxkrass.stundenplankotlinrefactor.commons.BaseActivity
import com.maxkrass.stundenplankotlinrefactor.data.Subject
import kotlinx.android.synthetic.main.activity_view_subject.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.setContentView

class ViewSubjectActivity : BaseActivity() {

    val subject: Subject by lazy { intent.getSerializableExtra("subject") as Subject }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewSubjectActivityUI().setContentView(this)
        view_subject_name.text = subject.name
        view_subject_teacher_name.text = subject.teacher
        view_subject_app_bar_layout.backgroundColor = subject.colorInt
        setActionBar(view_subject_toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }
}