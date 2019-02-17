package com.maxkrass.stundenplankotlinrefactor.extensions

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.GenericTypeIndicator

/**
 * Max made this for StundenplanKotlinRefactor on 09.12.2017.
 */

inline fun <reified T : Any> DataSnapshot.getTypedValue(): T? {
    return getValue(object : GenericTypeIndicator<T>() {})
}
inline fun <reified T : Any> DataSnapshot.getValueAs(): T? {
    return getValue(object : GenericTypeIndicator<T>() {})
}