@file:JvmName("AndroidExtensionsUtils")

package com.maxkrass.stundenplankotlinrefactor.extensions

import android.app.Activity
import android.content.SharedPreferences
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.updatePaddingRelative
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.data.Weekday
import com.maxkrass.stundenplankotlinrefactor.main.MainActivityFragment
import org.jetbrains.anko.internals.AnkoInternals

/**
 * Max made this for StundenplanKotlinRefactor on 22.07.2017.
 */

inline fun <reified T : Activity> MainActivityFragment<*, *>.startActivity(vararg params: Pair<String, Any?>) {
    AnkoInternals.internalStartActivity(requireContext(), T::class.java, params)
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

infix fun View.setBackgroundColorId(@ColorRes colorId: Int) {
    ContextCompat.getColor(this.context, colorId)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

var View.startPadding: Int
    inline get() = paddingStart
    set(value) = updatePaddingRelative(start = value)

var View.endPadding: Int
    inline get() = paddingEnd
    set(value) = updatePaddingRelative(end = value)

var TextView.textAppearanceResource: Int
    get() = AnkoInternals.noGetter()
    set(value) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(value)
    } else {
        @Suppress("DEPRECATION")
        setTextAppearance(context, value)
    }

val EditText.asString: String get() = text.toString()

fun <T> View.bind(binder: Binder<T>, binding: (item: T) -> Unit) = binder.bind(this.id, binding)

fun <T> View.unBind(binder: Binder<T>) = binder.unBind(this.id)

operator fun <T : ViewGroup> Array<T>.get(weekday: Weekday): T = get(weekday.ordinal)

infix fun SharedPreferences.Editor.put(pair: Pair<String, Any>) {
    val key = pair.first
    val value = pair.second
    when (value) {
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Boolean -> putBoolean(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
        else -> error("Only primitive types can be stored in SharedPreferences")
    }
}

private operator fun <T : RelativeLayout> Array<T>.get(weekday: Weekday): RelativeLayout =
        this[weekday.ordinal]
