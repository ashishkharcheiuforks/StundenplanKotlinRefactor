package com.maxkrass.stundenplankotlinrefactor.extensions

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.maxkrass.stundenplankotlinrefactor.R
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent

/**
 * Max made this for StundenplanKotlinRefactor on 20.09.2017.
 */

var Toolbar.startContentInset: Int
    inline get() = contentInsetStart
    set(value) = setContentInsetsRelative(value, contentInsetEnd)

inline fun ViewManager.divider(init: View.() -> Unit): View {
    return ankoView(
            { ctx ->
                View(ctx).apply {
                    backgroundResource = R.color.grey600
                    layoutParams = LinearLayout.LayoutParams(
                            matchParent, dip(1))
                }
            }, theme = 0, init = init)
}

inline fun ViewManager.navigationView(): NavigationView = navigationView() {}
inline fun ViewManager.navigationView(init: (@AnkoViewDslMarker NavigationView).() -> Unit): NavigationView {
    return ankoView({ ctx: Context -> NavigationView(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.recyclerView(): RecyclerView = recyclerView() {}
inline fun ViewManager.recyclerView(init: (@AnkoViewDslMarker _RecyclerView).() -> Unit): RecyclerView {
    return ankoView({ctx: Context -> _RecyclerView(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.drawerLayout(): DrawerLayout = drawerLayout() {}
inline fun ViewManager.drawerLayout(init: (@AnkoViewDslMarker _DrawerLayout).() -> Unit): DrawerLayout {
    return ankoView({ ctx: Context -> _DrawerLayout(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.themedCardView(theme: Int = 0): CardView = themedCardView(theme) {}
inline fun ViewManager.themedCardView(theme: Int = 0, init: (@AnkoViewDslMarker CardView).() -> Unit): CardView {
    return ankoView({ ctx: Context -> CardView(ctx) }, theme) { init() }
}

open class _DrawerLayout(ctx: Context): DrawerLayout(ctx) {

    inline fun <T: View> T.lparams(
            c: Context?,
            attrs: AttributeSet?,
            init: DrawerLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            c: Context?,
            attrs: AttributeSet?
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            init: DrawerLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            gravity: Int,
            init: DrawerLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(width, height, gravity)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            gravity: Int
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(width, height, gravity)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: DrawerLayout.LayoutParams?,
            init: DrawerLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: DrawerLayout.LayoutParams?
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: ViewGroup.LayoutParams?,
            init: DrawerLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: ViewGroup.MarginLayoutParams?,
            init: DrawerLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

}

open class _RecyclerView(ctx: Context): RecyclerView(ctx) {

    inline fun <T: View> T.lparams(
            c: Context?,
            attrs: AttributeSet?,
            init: RecyclerView.LayoutParams.() -> Unit
    ): T {
        val layoutParams = RecyclerView.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            c: Context?,
            attrs: AttributeSet?
    ): T {
        val layoutParams = RecyclerView.LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            init: RecyclerView.LayoutParams.() -> Unit
    ): T {
        val layoutParams = RecyclerView.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = RecyclerView.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: ViewGroup.MarginLayoutParams?,
            init: RecyclerView.LayoutParams.() -> Unit
    ): T {
        val layoutParams = RecyclerView.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = RecyclerView.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: ViewGroup.LayoutParams?,
            init: RecyclerView.LayoutParams.() -> Unit
    ): T {
        val layoutParams = RecyclerView.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = RecyclerView.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: RecyclerView.LayoutParams?,
            init: RecyclerView.LayoutParams.() -> Unit
    ): T {
        val layoutParams = RecyclerView.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T: View> T.lparams(
            source: RecyclerView.LayoutParams?
    ): T {
        val layoutParams = RecyclerView.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

}