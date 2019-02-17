package com.maxkrass.stundenplankotlinrefactor.manageteachers

import android.view.View
import android.view.ViewTreeObserver
import com.maxkrass.stundenplankotlinrefactor.R.id.teachers_recycler_view
import com.maxkrass.stundenplankotlinrefactor.extensions.recyclerView
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

/**
 * Max made this for StundenplanKotlinRefactor on 10.10.2017.
 */

class ManageTeachersFragmentUI : AnkoComponent<ManageTeachersFragment> {
    override fun createView(ui: AnkoContext<ManageTeachersFragment>): View = with(ui) {
        frameLayout {

            recyclerView {
                id = teachers_recycler_view
                clipToPadding = false
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(ctx)
                setHasFixedSize(true)

                adapter = owner.presenter.teachersAdapter

                viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        viewTreeObserver.removeOnPreDrawListener(this)
                        owner.activity?.startPostponedEnterTransition()
                        return true
                    }
                })

                lparams(width = matchParent, height = matchParent)
            }
        }
    }
}
