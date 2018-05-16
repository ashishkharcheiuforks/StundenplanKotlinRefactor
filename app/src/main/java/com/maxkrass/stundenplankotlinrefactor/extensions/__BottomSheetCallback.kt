package com.maxkrass.stundenplankotlinrefactor.extensions

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * Max made this for StundenplanKotlinRefactor on 30.11.2017.
 */
class __BottomSheetCallback : BottomSheetBehavior.BottomSheetCallback() {

    private var _onSlide: ((bottomSheet: View, slideOffset: Float) -> Unit)? = null
    private var _onStateChanged: ((bottomSheet: View, newState: Int) -> Unit)? = null

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        _onSlide?.invoke(bottomSheet, slideOffset)
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        _onStateChanged?.invoke(bottomSheet, newState)
    }

    fun onSlide(func: (bottomSheet: View, slideOffset: Float) -> Unit) {
        _onSlide = func
    }

    fun onStateChanged(func: (bottomSheet: View, newState: Int) -> Unit) {
        _onStateChanged = func
    }

}

inline fun <T : View> BottomSheetBehavior<T>.setBottomSheetCallback(func: __BottomSheetCallback.() -> Unit) {
    setBottomSheetCallback(__BottomSheetCallback().apply(func))
}
