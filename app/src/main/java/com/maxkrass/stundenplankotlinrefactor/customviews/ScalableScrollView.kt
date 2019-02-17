package com.maxkrass.stundenplankotlinrefactor.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ScrollView

/**
 * Max made this for StundenplanKotlinRefactor on 20.05.2017.
 */

class ScalableScrollView : ScrollView {

    private var scaleGestureDetector: ScaleGestureDetector? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        super.dispatchTouchEvent(event)
        return scaleGestureDetector?.onTouchEvent(event) ?: false
    }

    fun setScaleDetector(scaleDetector: ScaleGestureDetector) {
        scaleGestureDetector = scaleDetector
    }
}
