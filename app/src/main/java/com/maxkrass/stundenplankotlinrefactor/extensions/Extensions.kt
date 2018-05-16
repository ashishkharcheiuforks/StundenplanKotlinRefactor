@file:JvmName("ExtensionsUtils")

package com.maxkrass.stundenplankotlinrefactor.extensions

import android.content.res.Resources

/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */

fun Int.asDp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Int.asPx(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.asSp(): Float = (this * Resources.getSystem().displayMetrics.scaledDensity)

fun Boolean.toInt() = if (this) 1 else 0

fun String.toCamelCase(): String =
        this.split("_").joinToString("") { it -> it.capitalize() }

val Any.tag: String?
    get() = this::class.simpleName
