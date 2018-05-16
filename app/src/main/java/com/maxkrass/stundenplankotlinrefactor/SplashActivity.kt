package com.maxkrass.stundenplankotlinrefactor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.maxkrass.stundenplankotlinrefactor.main.MainActivity


/**
 * Max made this for StundenplanKotlinRefactor on 28.05.2017.
 */
class SplashActivity : AppCompatActivity() {

    private val googleTosUrl = "https://www.google.com/policies/terms/"
    private val rcSignIn = 7001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            startAuthUI()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == rcSignIn) {

            if (resultCode == RESULT_OK) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                return
            }

            if (resultCode == RESULT_CANCELED) {
                startAuthUI()
            }
        }
    }

    private fun startAuthUI() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(R.style.AppTheme)
                        .setLogo(R.mipmap.ic_launcher)
                        .setAvailableProviders(listOf(
                                AuthUI.IdpConfig.GoogleBuilder().build()
                        ))
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .setTosUrl(googleTosUrl)
                        .build(),
                rcSignIn)
    }
}
