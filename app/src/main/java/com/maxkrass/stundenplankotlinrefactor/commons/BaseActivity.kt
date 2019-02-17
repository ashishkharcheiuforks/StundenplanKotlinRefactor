package com.maxkrass.stundenplankotlinrefactor.commons

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.customviews.CheckBoxWidget
import org.jetbrains.anko.ctx

/**
 * Max made this for StundenplanKotlinRefactor on 20.09.2017.
 */

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = ContextCompat.getColor(ctx, R.color.status_bar_color)
        }
    }

    override fun onClick(view: View) {
        (view as? CheckBoxWidget)?.toggle()
    }

    val uid: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid
}
