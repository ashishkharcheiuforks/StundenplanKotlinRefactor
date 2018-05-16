package com.maxkrass.stundenplankotlinrefactor.extensions

import android.app.Activity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.jetbrains.anko.internals.AnkoInternals

/**
 * Max made this for StundenplanKotlinRefactor on 30.11.2017.
 */

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

inline fun FragmentManager.inTransactionNow(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commitNow()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, tag: String = "") {
    supportFragmentManager.inTransaction { add(frameId, fragment, tag) }
}

fun AppCompatActivity.addFragmentNow(fragment: Fragment, frameId: Int, tag: String = "") {
    supportFragmentManager.inTransactionNow { add(frameId, fragment, tag) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, tag: String = "") {
    supportFragmentManager.inTransaction { replace(frameId, fragment, tag) }
}

fun AppCompatActivity.replaceFragmentNow(fragment: Fragment, frameId: Int, tag: String = "") {
    supportFragmentManager.inTransactionNow { replace(frameId, fragment, tag) }
}

@Deprecated("Calling the FragmentManager directly is anti-pattern",
            ReplaceWith("addFragmentNow(fragment, containerViewId, tag)",
                        "com.maxkrass.stundenplankotlinrefactor.extensions.addFragmentNow"))
fun FragmentManager.addNow(@IdRes containerViewId: Int, fragment: Fragment, tag: String?) {
    this.beginTransaction().add(containerViewId, fragment, tag).commitNow()
}

@Deprecated("Calling the FragmentManager directly is anti-pattern",
            ReplaceWith("addFragment(fragment, containerViewId, tag)",
                        "com.maxkrass.stundenplankotlinrefactor.extensions.addFragment"))
fun FragmentManager.add(@IdRes containerViewId: Int, fragment: Fragment, tag: String?) {
    this.beginTransaction().add(containerViewId, fragment, tag).commit()
}

@Deprecated("Calling the FragmentManager directly is anti-pattern",
            ReplaceWith("replaceFragmentNow(fragment, containerViewId, tag)",
                        "com.maxkrass.stundenplankotlinrefactor.extensions.replaceFragmentNow"))
fun FragmentManager.replaceNow(@IdRes containerViewId: Int, fragment: Fragment, tag: String?) {
    this.beginTransaction().replace(containerViewId, fragment, tag).commitNow()
}

inline fun <reified T : Fragment> AppCompatActivity.findFragmentByTag(tag: String,
                                                                      ifNone: (String) -> T): T
        = supportFragmentManager.findFragmentByTag(tag) as T? ?: ifNone(tag)

inline fun <reified T: Activity> Fragment.startActivityForResult(requestCode: Int, options: Bundle, vararg params: Pair<String, Any>) {
    startActivityForResult(AnkoInternals.createIntent(requireActivity(), T::class.java, params), requestCode, options)
}

var Toolbar.titleRes: Int
    get() = 0
    set(id) {
        this.title = this.context.getString(id)
    }
