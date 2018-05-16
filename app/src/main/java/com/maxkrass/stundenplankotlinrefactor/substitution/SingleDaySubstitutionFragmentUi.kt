package com.maxkrass.stundenplankotlinrefactor.substitution

import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import com.maxkrass.stundenplankotlinrefactor.extensions.bind
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * Max made this for StundenplanKotlinRefactor on 29.11.2017.
 */
class SingleDaySubstitutionFragmentUi(private val recyclerAdapter: SingleDaySubRecyclerAdapterUnified, private val selectedEvent: Binder<SubstitutionEvent>) : AnkoComponent<SingleDaySubstitutionFragment> {

    lateinit var scrollView: NestedScrollView
    lateinit var addSubstitutionSubject: FloatingActionButton

    override fun createView(ui: AnkoContext<SingleDaySubstitutionFragment>): View = with(ui) {
        coordinatorLayout {
            lparams(width = matchParent, height = matchParent)

            owner.swipeRefreshLayout = swipeRefreshLayout {

                id = R.id.swipe_refresh_layout

                recyclerView {
                    id = R.id.substitution_card_list
                    clipToPadding = false
                    bottomPadding = dip(4)
                    topPadding = dip(4)
                    layoutManager = LinearLayoutManager(ctx)
                    adapter = recyclerAdapter
                    isVerticalScrollBarEnabled = false
                    lparams(width = matchParent, height = matchParent)
                }


            }.lparams(width = matchParent, height = matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }

            scrollView = nestedScrollView {
                id = R.id.substitution_bottom_sheet
                clipToPadding = true
                backgroundColorResource = R.color.cardview_light_background

                verticalLayout {

                    appBarLayout {

                        id = R.id.substitution_bottom_sheet_app_bar

                        toolbar {
                            backgroundColorResource = R.color.material_teal
                            bind(selectedEvent) {
                                title = "${it.grade}: ${it.period}. Std."
                            }
                            setTitleTextAppearance(ctx, R.style.TextAppearance_AppCompat_Display2)
                            setTitleTextColor(ContextCompat.getColor(ctx, R.color.material_white))
                        }.lparams(width = matchParent)

                        toolbar {
                            backgroundColorResource = R.color.material_teal
                            bind(selectedEvent) {
                                title = "${it.oldTeacher} ${it.subject}"
                            }
                            setTitleTextAppearance(ctx, R.style.TextAppearance_AppCompat_Display1)
                            setTitleTextColor(ContextCompat.getColor(ctx, R.color.material_white))
                        }.lparams(width = matchParent)

                    }.lparams(width = matchParent)

                    verticalLayout {
                        padding = dip(16)

                        textView {
                            bind(selectedEvent) {
                                text = it.typeText
                            }
                        }

                        textView {
                            bind(selectedEvent) {
                                text = it.annotation
                            }
                        }

                    }.lparams(width = matchParent)

                }.lparams(width = matchParent)

            }.lparams(width = matchParent) {
                behavior = BottomSheetBehavior<NestedScrollView>().apply {
                    isHideable = true
                    peekHeight = dip(116)
                }
            }

            addSubstitutionSubject = floatingActionButton {

                id = R.id.add_substitution_subject
                size = FloatingActionButton.SIZE_MINI
                visibility = View.GONE
                imageResource = R.drawable.ic_add_24dp

            }.lparams {
                marginEnd = dip(16)
                anchorId = R.id.substitution_bottom_sheet_app_bar
                anchorGravity = Gravity.TOP or Gravity.END
            }
        }


    }
}
