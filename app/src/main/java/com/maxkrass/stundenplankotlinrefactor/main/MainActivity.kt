package com.maxkrass.stundenplankotlinrefactor.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.extensions.SingleLiveEvent
import com.maxkrass.stundenplankotlinrefactor.extensions.hide
import com.maxkrass.stundenplankotlinrefactor.extensions.show
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.setContentView
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

// const val OPEN_SUBSTITUTIONS_REQUEST_CODE = 0x0431

class MainActivity : AppCompatActivity(), KodeinAware, AnkoLogger {

    companion object {
        val fabClickEvent = SingleLiveEvent<View>()
    }

    override val kodein by kodein()

    private val ui: MainActivityUI by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui.setContentView(this)

        setSupportActionBar(toolbar)

        main_fab.setOnClickListener {
            fabClickEvent.call(it)
        }

        val bottomNavController = Navigation.findNavController(this, R.id.my_nav_host_fragment)
        NavigationUI.setupWithNavController(main_bottom_navigation, bottomNavController)
        bottomNavController.addOnDestinationChangedListener { _, destination, _ ->
            // Update UI visibility and other events
            toolbar.title = destination.label
            when (destination.id) {
                R.id.substitutionPlanFragment -> main_tab_layout.show()
                else -> main_tab_layout.hide()
            }
            when (destination.id) {
                R.id.stundenplanFragment, R.id.manageSubjectsFragment -> main_fab.show()
                else -> main_fab.hide()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        NavigationUI.onNavDestinationSelected(item, Navigation.findNavController(this, R.id.my_nav_host_fragment))
        return super.onOptionsItemSelected(item)
    }
}
