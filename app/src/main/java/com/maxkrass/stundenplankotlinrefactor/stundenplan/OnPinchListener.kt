package com.maxkrass.stundenplankotlinrefactor.stundenplan

import android.view.ScaleGestureDetector

/**
 * Max made this for StundenplanKotlinRefactor on 18.07.2017.
 */
class OnPinchListener(private val adapter: StundenplanAdapter) : ScaleGestureDetector.SimpleOnScaleGestureListener() {

    private var startingSpan: Float = 0.0f
    private var mLastScalingFactor: Float = 1.0f

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        adapter.apply {
            mScalingFactor = mLastScalingFactor * detector.currentSpanY / startingSpan
            when {
                mScalingFactor < 0.75 -> mScalingFactor = 0.75f
                mScalingFactor < 1.0 -> showRoomOnSingleLesson = false
                mScalingFactor > 1.5 -> mScalingFactor = 1.5f
                else -> showRoomOnSingleLesson = true
            }

            adapter.scaleViews()
        }

        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        startingSpan = detector.currentSpanY
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector) {
        mLastScalingFactor = adapter.mScalingFactor
    }
}
