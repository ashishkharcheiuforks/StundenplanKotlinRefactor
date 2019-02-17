package com.maxkrass.stundenplankotlinrefactor.main

import com.google.firebase.auth.FirebaseAuth
import com.maxkrass.stundenplankotlinrefactor.data.Uid
import net.grandcentrix.thirtyinch.TiFragment
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.TiView
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

/**
 * Max made this for StundenplanKotlinRefactor on 05.09.2017.
 */
abstract class MainActivityFragment<P : TiPresenter<V>, V : TiView> : TiFragment<P, V>(), KodeinAware  {

    override val kodein: Kodein by kodein()

    private val auth by instance<FirebaseAuth>()

    internal val uid: Uid = auth.currentUser?.uid
            ?: throw IllegalStateException("User mustn't be null")
}