package com.maxkrass.stundenplankotlinrefactor.substitution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.data.Grade
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import com.maxkrass.stundenplankotlinrefactor.extensions.setBottomSheetCallback
import net.grandcentrix.thirtyinch.TiFragment
import org.jetbrains.anko.AnkoContext

class SingleDaySubstitutionFragment :
        TiFragment<SingleDaySubstitutionPresenter, SingleDaySubstitutionContract.View>(),
        SingleDaySubstitutionContract.View,
        SingleDaySubRecyclerAdapterUnified.CardViewItemViewHolder.Host,
        SingleDaySubRecyclerAdapterUnified.OnLoadingFinishedListener,
        SingleDaySubRecyclerAdapterUnified.OnSubstitutionItemClickListener {

    private val recyclerAdapter: SingleDaySubRecyclerAdapterUnified by lazy { SingleDaySubRecyclerAdapterUnified(this, requireContext(), this, this) }
    private lateinit var mPagerAdapter: SubstitutionPlanPagerAdapter
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<*>
    internal lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val ui: SingleDaySubstitutionFragmentUi by lazy { SingleDaySubstitutionFragmentUi(recyclerAdapter, selectedEvent) }
    private var index: Int = 0
    private var mUId: String = ""
    private var mLastChecked = System.currentTimeMillis()
    private val substitutionSubjects = mutableMapOf<String, Grade>()
    override val selectedEvent: Binder<SubstitutionEvent> = Binder(SubstitutionEvent())
    var title: String = ""
        private set

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = ui.createView(AnkoContext.create(requireContext(),
                this))

        swipeRefreshLayout.setOnRefreshListener {
            if (mLastChecked < System.currentTimeMillis() - IDLE_TIME) {
                presenter.refreshItems()
                mLastChecked = System.currentTimeMillis()
            } else {
                onLoadingFinished()
            }
        }

        mPagerAdapter.tabLayout.getTabAt(index - 1)?.text = title

        if (savedInstanceState != null && mUId.isBlank()) {
            mUId = savedInstanceState.getString("uId")
        }

        mBottomSheetBehavior = BottomSheetBehavior.from(ui.scrollView).apply {
            setBottomSheetCallback {
                onStateChanged { _, newState ->
                    if (newState == BottomSheetBehavior.STATE_EXPANDED &&
                        !substitutionSubjects.containsKey(selectedEvent.item.subject)) {
                        ui.addSubstitutionSubject.show()
                    } else {
                        ui.addSubstitutionSubject.hide()
                    }
                }
            }
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        ui.addSubstitutionSubject.setOnClickListener { presenter.addSubstitutionSubjectClicked() }
        return view
    }

    override fun setTabTitle(title: String) {
        mPagerAdapter.tabLayout.getTabAt(index - 1)?.text = title
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showConnectionError() {
        Snackbar.make(swipeRefreshLayout,
                      "Bitte überprüfe deine Internetverbindung",
                      Snackbar.LENGTH_LONG).show()
    }

    override fun updateList(events: List<SubstitutionEvent>) {
        recyclerAdapter.setNewContent(events)
    }

    override fun providePresenter(): SingleDaySubstitutionPresenter =
            SingleDaySubstitutionPresenter(mUId, index)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("uId", mUId)
    }

    override fun onLoadingFinished() {
        hideLoading()
    }

    override fun onItemClick(event: SubstitutionEvent) {
        selectedEvent.item = event
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    companion object {
        private const val IDLE_TIME: Long = 60000

        fun newInstance(index: Int,
                        uId: String,
                        pagerAdapter: SubstitutionPlanPagerAdapter): SingleDaySubstitutionFragment {
            val fragment = SingleDaySubstitutionFragment()
            fragment.mPagerAdapter = pagerAdapter
            fragment.index = index
            fragment.mUId = uId
            when (index) {
                1    -> fragment.title = "Heute"
                2    -> fragment.title = "Morgen"
                3    -> fragment.title = "Übermorgen"
                else -> fragment.title = ""
            }
            return fragment
        }
    }
}