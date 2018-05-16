package com.maxkrass.stundenplankotlinrefactor.main

import android.view.Gravity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.extensions.drawerLayout
import com.maxkrass.stundenplankotlinrefactor.extensions.navigationView
import org.jetbrains.anko.*

class MainActivityUI : AnkoComponent<MainActivity> {

    lateinit var mainNavigation: NavigationView
    lateinit var mainDrawer: DrawerLayout

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        mainDrawer = drawerLayout {

            id = R.id.main_drawer_layout

            include<CoordinatorLayout>(R.layout.app_bar_main)

            mainNavigation = navigationView {

                id = R.id.main_navigation

                fitsSystemWindows = true

                inflateHeaderView(R.layout.nav_header_main)
                inflateMenu(R.menu.activity_main_drawer)

                setNavigationItemSelectedListener(ui.owner)

            }.lparams(
                    width = wrapContent,
                    height = matchParent,
                    gravity = Gravity.START)

            fitsSystemWindows = true
        }
        return@with mainDrawer
    }
}
