package com.maxkrass.stundenplankotlinrefactor.stundenplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.commons.IStundenplanView
import com.maxkrass.stundenplankotlinrefactor.data.Lessons
import com.maxkrass.stundenplankotlinrefactor.data.Subjects
import com.maxkrass.stundenplankotlinrefactor.extensions.longSnackbar
import com.maxkrass.stundenplankotlinrefactor.extensions.setBackgroundColorId
import com.maxkrass.stundenplankotlinrefactor.main.MainActivity
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import kotlinx.android.synthetic.main.fragment_stundenplan.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */
class StundenplanFragment : MainActivityFragment<StundenplanPresenter, IStundenplanView>(),
        IStundenplanView,
        AnkoLogger {

    override fun updateWeekdayColumn(lessons: Lessons, subjects: Subjects) {
        info("updating column")
        stundenplanAdapter.addLessons(lessons, subjects)
    }

    override fun showLoadingError() {
        this.view?.longSnackbar("Error while loading")
    }

    override fun providePresenter(): StundenplanPresenter {
        info("providing presenter")
        return StundenplanPresenter()
    }

    private val stundenplanAdapter: StundenplanAdapter by lazy {
        StundenplanAdapter(arrayOf(
                column_monday,
                column_tuesday,
                column_wednesday,
                column_thursday,
                column_friday
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_stundenplan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.fabClickEvent.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(R.id.action_create_lesson, null)
        })

        stundenplan_container.setScaleDetector(ScaleGestureDetector(activity,
                OnPinchListener(stundenplanAdapter)))

        when (Calendar.getInstance()[Calendar.DAY_OF_WEEK]) {
            Calendar.MONDAY -> column_monday setBackgroundColorId R.color.divider_black
            Calendar.TUESDAY -> column_tuesday setBackgroundColorId R.color.divider_black
            Calendar.WEDNESDAY -> column_wednesday setBackgroundColorId R.color.divider_black
            Calendar.THURSDAY -> column_thursday setBackgroundColorId R.color.divider_black
            Calendar.FRIDAY -> column_friday setBackgroundColorId R.color.divider_black
        }
    }
}
