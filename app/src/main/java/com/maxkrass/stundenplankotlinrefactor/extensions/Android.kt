@file:JvmName("AndroidExtensionsUtils")

package com.maxkrass.stundenplankotlinrefactor.extensions

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.view.*
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.updatePaddingRelative
import com.maxkrass.stundenplankotlinrefactor.commons.Binder
import com.maxkrass.stundenplankotlinrefactor.data.Weekday
import org.jetbrains.anko.internals.AnkoInternals
import java.io.Serializable

/**
 * Max made this for StundenplanKotlinRefactor on 22.07.2017.
 */

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

infix fun View.setBackgroundColorId(@ColorRes colorId: Int) {
    ContextCompat.getColor(this.context, colorId)
}

infix fun Intent.getBooleanExtra(name: String): Boolean = this.getBooleanExtra(name, false)

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.visibility(show: Boolean) {
    if (show) show()
    else hide()
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


fun Menu.uncheckAll() {
    forEach {
        it.isChecked = false
    }
}

infix fun Menu.checkOnly(item: MenuItem) {
    forEach {
        it.isChecked = false
    }
    item.isChecked = true
}


val EditText.asString: String get() = text.toString()


fun <T> View.bind(binder: Binder<T>, binding: (item: T) -> Unit) = binder.bind(this.id, binding)

fun <T> View.unBind(binder: Binder<T>) = binder.unBind(this.id)


operator fun <T : ViewGroup> Array<T>.get(weekday: Weekday): T = get(weekday.ordinal)

infix fun SharedPreferences.Editor.put(pair: Pair<String, Any>) {
    val key = pair.first
    val value = pair.second
    when (value) {
        is String  -> putString(key, value)
        is Int     -> putInt(key, value)
        is Boolean -> putBoolean(key, value)
        is Long    -> putLong(key, value)
        is Float   -> putFloat(key, value)
        else       -> error("Only primitive types can be stored in SharedPreferences")
    }
}

infix fun Bundle.put(pair: Pair<String, Any?>) {
    val (key, value) = pair
    when (value) {
        is Boolean      -> putBoolean(key, value)
        is Byte         -> putByte(key, value)
        is Short        -> putShort(key, value)
        is Int          -> putInt(key, value)
        is Long         -> putLong(key, value)
        is Float        -> putFloat(key, value)
        is Double       -> putDouble(key, value)
        is Char         -> putChar(key, value)
        is CharSequence -> putCharSequence(key, value)
        is String       -> putString(key, value)
        is Size         -> putSize(key, value)
        is SizeF        -> putSizeF(key, value)
        is Serializable -> putSerializable(key, value)
        is Parcelable   -> putParcelable(key, value)
        is IBinder      -> putBinder(key, value)
        is Bundle       -> putBundle(key, value)

        is Array<*>     -> error("Cannot put primitive array in bundle with this method")
        is List<*>      -> error("Cannot put primitive list in bundle with this method")
        else            -> error("Cannot determine type of passed value")
    }
}

private operator fun <T : RelativeLayout> Array<T>.get(weekday: Weekday): RelativeLayout =
        this[weekday.ordinal]
