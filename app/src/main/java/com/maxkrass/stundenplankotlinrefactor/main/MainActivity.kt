package com.maxkrass.stundenplankotlinrefactor.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.extensions.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.setContentView
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.with


const val MAIN_FRAGMENT_TAG: String = "stundenplan"
const val GRADE_CALCULATOR_TAG: String = "grade_calculator"
const val SUBSTITUTION_PLAN_TAG: String = "substitution_plan"
const val MANAGE_TEACHERS_TAG: String = "manage_teachers"
const val SETTINGS_FRAGMENT_TAG: String = "settings"
const val MANAGE_SUBJECTS_TAG: String = "manage_subjects"
const val NO_FRAGMENT_TAG: String = "no_fragment"
const val OPEN_SUBSTITUTIONS_REQUEST_CODE = 0x0431

class MainActivity : AppCompatActivity(), KodeinAware, NavigationView.OnNavigationItemSelectedListener, AnkoLogger {

    override val kodein by closestKodein()

    private var user: FirebaseUser? = null
    private var lastFragmentTag: String = NO_FRAGMENT_TAG
    private val mFirebaseAuth: FirebaseAuth by instance()
    private val ui: MainActivityUI by instance()
    private val mAuthListener: FirebaseAuth.AuthStateListener by lazy {
        FirebaseAuth.AuthStateListener {
            user = it.currentUser
            user?.let {
                Log.d(this@MainActivity.tag,
                      "onAuthStateChanged:signed_in: " + it.uid)
                TODO("Finish here")
                /*AccountHeaderBuilder()
                        .withActivity(this@MainActivity)
                        .withHeaderBackground(R.color.material_grey)
                        .withDrawer(result)
                        .addProfiles(
                                ProfileDrawerItem()
                                        .withName(user.getDisplayName())
                                        .withEmail(user.getEmail())
                                        .withIcon(user.getPhotoUrl())
                        ).build()*/
            }
        }
    }

    private val mDrawerToggle by lazy {
        ActionBarDrawerToggle(
                this,
                ui.mainDrawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ).apply { ui.mainDrawer.addDrawerListener(this) }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle.syncState()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)

        setSupportActionBar(toolbar)
        savedInstanceState?.let {
            lastFragmentTag = it.getString("lastFragment")
        }
        if (intent.hasExtra("requestCode") && intent.getIntExtra("requestCode",
                                                                 0) == OPEN_SUBSTITUTIONS_REQUEST_CODE) {
            lastFragmentTag = SUBSTITUTION_PLAN_TAG
        }
        if (lastFragmentTag == NO_FRAGMENT_TAG) {
            lastFragmentTag = MAIN_FRAGMENT_TAG
            val mainFragment by instance<MainActivityFragment<*,*>>()
            addFragmentNow(mainFragment,
                           R.id.fragment_container,
                           lastFragmentTag)
            toolbar.title = mainFragment.toolbarTitle
            Log.d(tag, "onCreate: adding MainActivityFragment")
        } else {
            restoreUI()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.put("lastFragment" to lastFragmentTag)
    }

    override fun onStop() {
        super.onStop()
        mFirebaseAuth.removeAuthStateListener(mAuthListener)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else                 -> super.onOptionsItemSelected(item)
        }
    }

    private fun restoreUI() {
        val fragment by with(lastFragmentTag).instance<MainActivityFragment<*,*>>()
        toolbar.title = fragment.toolbarTitle
        main_tab_layout.visibility(fragment.showsTabs)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val fragmentTag: String = when (item.itemId) {
            R.id.drawer_main_item             -> MAIN_FRAGMENT_TAG
            R.id.drawer_substitution_item     -> SUBSTITUTION_PLAN_TAG
            R.id.drawer_teachers_item         -> MANAGE_TEACHERS_TAG
            R.id.drawer_subjects_item         -> MANAGE_SUBJECTS_TAG
            R.id.drawer_grade_calculator_item -> GRADE_CALCULATOR_TAG
            R.id.drawer_settings_item         -> SETTINGS_FRAGMENT_TAG
            else                              -> return false
        }
        return handleNavigationItemSlected(fragmentTag, item)
    }

    private fun handleNavigationItemSlected(fragmentTag: String, item: MenuItem): Boolean {
        if (supportFragmentManager.findFragmentByTag(fragmentTag) == null) {

            val mainActivityFragment by with(fragmentTag).instance<MainActivityFragment<*,*>>()
            replaceFragmentNow(mainActivityFragment,
                               fragment_container.id,
                               fragmentTag)
            toolbar.title = mainActivityFragment.toolbarTitle
            main_tab_layout.visibility(mainActivityFragment.showsTabs)
            lastFragmentTag = fragmentTag
            ui.mainDrawer.closeDrawers()
            ui.mainNavigation.menu checkOnly item
            return true
        }
        return false
    }
}
