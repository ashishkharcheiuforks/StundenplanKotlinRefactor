package com.maxkrass.stundenplankotlinrefactor.commons

import android.app.Application
import com.github.salomonbrys.kodein.*
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragmentFactory
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityUI
import com.squareup.leakcanary.LeakCanary

/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */
class StundenplanApplication: Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        bind() from singleton { FirebaseAuth.getInstance() }
        bind() from singleton { FirebaseMessaging.getInstance() }
        bind() from singleton { MainActivityUI() }
        bind<MainActivityFragment<*,*>>() with factory { tag: String -> MainActivityFragmentFactory.byTag(tag) }
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        if (FirebaseApp.getApps(this).isNotEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        }
    }

}
