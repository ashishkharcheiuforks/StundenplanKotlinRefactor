package com.maxkrass.stundenplankotlinrefactor.extensions

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

/**
 * Max made this for StundenplanKotlinRefactor on 30.11.2017.
 */

class __ValueEventListener : ValueEventListener {

    private var _onCancelled: ((error: DatabaseError) -> Unit)? = null
    private var _onDataChanged: ((snapshot: DataSnapshot) -> Unit)? = null

    override fun onCancelled(p0: DatabaseError) {
        _onCancelled?.invoke(p0)
    }

    override fun onDataChange(p0: DataSnapshot) {
        _onDataChanged?.invoke(p0)
    }

    fun onCancelled(func: (error: DatabaseError) -> Unit) {
        _onCancelled = func
    }

    fun onDataChange(func: (snapshot: DataSnapshot) -> Unit) {
        _onDataChanged = func
    }
}

inline fun DatabaseReference.addSingleEventListener(func: __ValueEventListener.() -> Unit) {
    addListenerForSingleValueEvent(__ValueEventListener().apply(func))
}

inline fun DatabaseReference.addValueEventListener(func: __ValueEventListener.() -> Unit) {
    addValueEventListener(__ValueEventListener().apply(func))
}