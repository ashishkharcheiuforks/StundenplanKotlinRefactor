package com.maxkrass.stundenplankotlinrefactor.main

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.maxkrass.stundenplankotlinrefactor.data.Uid
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment.Companion.NullMainActivityPresenter
import net.grandcentrix.thirtyinch.TiFragment
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.TiView
import java.lang.IllegalStateException


/**
 * Max made this for StundenplanKotlinRefactor on 05.09.2017.
 */
abstract class MainActivityFragment<P : TiPresenter<V>, V : TiView> : TiFragment<P, V>() {

    abstract val showsTabs: Boolean

    abstract val toolbarTitle: String

    internal val uid: Uid = FirebaseAuth.getInstance().currentUser?.uid ?: throw IllegalStateException(
            "User mustn't be null")

    companion object {
        fun createNullFragment(): MainActivityFragment<NullMainActivityPresenter, NullMainActivityView> =
                NullMainActivityFragment()

        @SuppressLint("ValidFragment")
        private class NullMainActivityFragment :
                MainActivityFragment<NullMainActivityPresenter, NullMainActivityView>() {
            override fun providePresenter(): NullMainActivityPresenter = NullMainActivityPresenter()
            override val showsTabs: Boolean = false
            override val toolbarTitle: String = "No Title"

        }

        class NullMainActivityPresenter : MainActivityPresenter<NullMainActivityView>()

        class NullMainActivityView : MainActivityView

        abstract class MainActivityPresenter<V : MainActivityView> : TiPresenter<V>()
        interface MainActivityView : TiView
    }

}
