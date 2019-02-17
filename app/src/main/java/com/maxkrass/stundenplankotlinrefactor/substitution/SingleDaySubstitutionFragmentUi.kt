package com.maxkrass.stundenplankotlinrefactor.substitution

import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import com.maxkrass.stundenplankotlinrefactor.extensions.*
import org.jetbrains.anko.*

/**
 * Max made this for StundenplanKotlinRefactor on 29.11.2017.
 */
@Suppress("MagicNumber")
class SingleDaySubstitutionFragmentUi(
    private val recyclerAdapter: SingleDaySubRecyclerAdapterUnified,
    private val selectedEvent: Binder<SubstitutionEvent>
) : AnkoComponent<SingleDaySubstitutionFragment> {

    lateinit var scrollView: NestedScrollView
    lateinit var addSubstitutionSubject: com.google.android.material.floatingactionbutton.FloatingActionButton

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
                    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(ctx)
                    adapter = recyclerAdapter
                    isVerticalScrollBarEnabled = false
                    lparams(width = matchParent, height = matchParent)
                }
            }.lparams(width = matchParent, height = matchParent) {
                behavior = com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior()
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
                behavior = com.google.android.material.bottomsheet.BottomSheetBehavior<NestedScrollView>().apply {
                    isHideable = true
                    peekHeight = dip(116)
                }
            }

            addSubstitutionSubject = floatingActionButton {

                id = R.id.add_substitution_subject
                size = com.google.android.material.floatingactionbutton.FloatingActionButton.SIZE_MINI
                hide()
                imageResource = R.drawable.ic_add_24dp
            }.lparams {
                marginEnd = dip(16)
                anchorId = R.id.substitution_bottom_sheet_app_bar
                anchorGravity = Gravity.TOP or Gravity.END
            }
        }
    }
}
