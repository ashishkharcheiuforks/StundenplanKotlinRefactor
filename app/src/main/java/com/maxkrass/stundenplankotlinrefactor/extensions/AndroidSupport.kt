package com.maxkrass.stundenplankotlinrefactor.extensions

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.jetbrains.anko.internals.AnkoInternals

/**
 * Max made this for StundenplanKotlinRefactor on 30.11.2017.
 */
inline fun <reified T : Fragment> AppCompatActivity.findFragmentByTag(
    tag: String,
    ifNone: (String) -> T
): T =
        supportFragmentManager.findFragmentByTag(tag) as T? ?: ifNone(tag)

inline fun <reified T : Activity> Fragment.startActivityForResult(
    requestCode: Int,
    options: Bundle,
    vararg params: Pair<String, Any>
) {
    startActivityForResult(
            AnkoInternals.createIntent(requireActivity(), T::class.java, params),
            requestCode,
            options)
}