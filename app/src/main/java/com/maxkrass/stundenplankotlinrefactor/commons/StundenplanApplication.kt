package com.maxkrass.stundenplankotlinrefactor.commons

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.maxkrass.stundenplankotlinrefactor.data.Uid
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityUI
import com.maxkrass.stundenplankotlinrefactor.managesubjects.ManageSubjectsPresenter
import com.maxkrass.stundenplankotlinrefactor.substitution.SingleDaySubstitutionFragment
import com.maxkrass.stundenplankotlinrefactor.substitution.SubstitutionPlanPagerAdapter
import com.squareup.leakcanary.LeakCanary
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.singleton

/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */
class StundenplanApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        bind<FirebaseAuth>() with singleton { FirebaseAuth.getInstance() }
        bind<FirebaseMessaging>() with singleton { FirebaseMessaging.getInstance() }
        bind<MainActivityUI>() with singleton { MainActivityUI() }
        bind<ManageSubjectsPresenter>() with factory { uid: Uid -> ManageSubjectsPresenter(uid) }
        bind<SingleDaySubstitutionFragment>("sdsf") with factory { index: Int, pagerAdapter: SubstitutionPlanPagerAdapter -> SingleDaySubstitutionFragment.newInstance(index, pagerAdapter) }
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
