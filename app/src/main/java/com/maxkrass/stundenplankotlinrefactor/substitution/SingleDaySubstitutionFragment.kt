package com.maxkrass.stundenplankotlinrefactor.substitution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.data.Grade
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import com.maxkrass.stundenplankotlinrefactor.extensions.addSingleEventListener
import com.maxkrass.stundenplankotlinrefactor.extensions.getTypedValue
import com.maxkrass.stundenplankotlinrefactor.extensions.setBottomSheetCallback
import net.grandcentrix.thirtyinch.TiFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SingleDaySubstitutionFragment :
        TiFragment<SingleDaySubstitutionPresenter, SingleDaySubstitutionContract.View>(),
        SingleDaySubstitutionContract.View,
        SingleDaySubRecyclerAdapterUnified.CardViewItemViewHolder.Host,
        SingleDaySubRecyclerAdapterUnified.OnLoadingFinishedListener,
        SingleDaySubRecyclerAdapterUnified.OnSubstitutionItemClickListener, KodeinAware {

    override val kodein: Kodein by kodein()

    private val recyclerAdapter: SingleDaySubRecyclerAdapterUnified by lazy {
        SingleDaySubRecyclerAdapterUnified(this, requireContext(), this, this)
    }
    private lateinit var mPagerAdapter: SubstitutionPlanPagerAdapter

    private val mBottomSheetBehavior: BottomSheetBehavior<*> by lazy {
        BottomSheetBehavior.from(ui.scrollView).apply {
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
    }

    internal lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val ui: SingleDaySubstitutionFragmentUi by lazy {
        SingleDaySubstitutionFragmentUi(recyclerAdapter, selectedEvent)
    }
    private var index: Int = 0
    private val auth: FirebaseAuth by instance()
    private val uid: String by lazy { auth.uid ?: "" }
    private var mLastChecked = System.currentTimeMillis()
    private val substitutionSubjects = mutableMapOf<String, Grade>()
    override val selectedEvent: Binder<SubstitutionEvent> = Binder(SubstitutionEvent())
    var title: String = ""
        private set

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return ui.createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout.setOnRefreshListener {
            if (mLastChecked < System.currentTimeMillis() - IDLE_TIME) {
                presenter.refreshItems()
                mLastChecked = System.currentTimeMillis()
            } else {
                onLoadingFinished()
            }
        }

        setTabTitle(title)

        ui.addSubstitutionSubject.onClick { presenter.addSubstitutionSubjectClicked() }
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
        swipeRefreshLayout.longSnackbar( "Bitte überprüfe deine Internetverbindung")
    }

    override fun updateList(events: List<SubstitutionEvent>) {
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            FirebaseDatabase
                    .getInstance()
                    .reference
                    .child("substitutionSubjects")
                    .child(it)
                    .addSingleEventListener {
                        onDataChange { snapshot ->
                            val savedSubjects = snapshot.getTypedValue<HashMap<String, String>>()
                                    ?: emptyMap<String, String>()
                            recyclerAdapter.submitList(SubstitutionEventGroupUtils
                                    .convertEventListToGroupList(requireContext(), events, savedSubjects))
                        }
                    }
        }
    }

    override fun providePresenter(): SingleDaySubstitutionPresenter =
            SingleDaySubstitutionPresenter(uid, index)

    override fun onLoadingFinished() {
        hideLoading()
    }

    override fun onItemClick(event: SubstitutionEvent) {
        selectedEvent.item = event
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    companion object {
        private const val IDLE_TIME: Long = 60000

        fun newInstance(
                index: Int, pagerAdapter: SubstitutionPlanPagerAdapter): SingleDaySubstitutionFragment {
            val fragment = SingleDaySubstitutionFragment()
            fragment.mPagerAdapter = pagerAdapter
            fragment.index = index
            fragment.title = when (index) {
                1 -> "Heute"
                2 -> "Morgen"
                3 -> "Übermorgen"
                else -> ""
            }
            return fragment
        }
    }
}