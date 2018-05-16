package com.maxkrass.stundenplankotlinrefactor.commons

import kotlin.properties.Delegates

/**
 * Max made this for StundenplanKotlinRefactor on 10.10.2017.
 */
class Binder<T>(initValue: T, distintUntilChanged: Boolean = true) {
    private val bound: MutableMap<Int, (item: T) -> Unit> = HashMap()
    var item: T by Delegates.observable(initValue) { _, old, new ->
        if (distintUntilChanged) {
            if (old != new) refresh(new)
        } else {
            refresh(new)
        }
    }

    fun bind(id: Int, binding: (item: T) -> Unit) {
        bound.put(id, binding)
        binding(item)
    }

    fun unBind(id: Int) = bound.remove(id)

    private fun refresh(new: T) {
        bound.values.forEach {
            it(new)
        }
    }
}