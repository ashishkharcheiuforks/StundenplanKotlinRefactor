@file:Suppress("NOTHING_TO_INLINE", "unused", "ClassName")

package com.maxkrass.stundenplankotlinrefactor.extensions

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.maxkrass.stundenplankotlinrefactor.R
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent

/**
 * Max made this for StundenplanKotlinRefactor on 20.09.2017.
 */

@Suppress("TooManyFunctions", "ClassNaming")
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

inline fun ViewManager.navigationView(): NavigationView = navigationView {}
inline fun ViewManager.navigationView(init: (@AnkoViewDslMarker NavigationView).() -> Unit): NavigationView {
    return ankoView({ ctx: Context -> NavigationView(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.recyclerView(): RecyclerView = recyclerView {}
inline fun ViewManager.recyclerView(init: (@AnkoViewDslMarker _RecyclerView).() -> Unit): RecyclerView {
    return ankoView({ ctx: Context -> _RecyclerView(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.drawerLayout(): DrawerLayout = drawerLayout {}
inline fun ViewManager.drawerLayout(init: (@AnkoViewDslMarker _DrawerLayout).() -> Unit): DrawerLayout {
    return ankoView({ ctx: Context -> _DrawerLayout(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.themedCardView(theme: Int = 0): CardView = themedCardView(theme) {}
inline fun ViewManager.themedCardView(theme: Int = 0, init: (@AnkoViewDslMarker CardView).() -> Unit): CardView {
    return ankoView({ ctx: Context -> CardView(ctx) }, theme) { init() }
}

inline fun ViewManager.viewPager(): ViewPager = viewPager {}
inline fun ViewManager.viewPager(init: (@AnkoViewDslMarker ViewPager).() -> Unit): ViewPager {
    return ankoView({ ctx: Context -> ViewPager(ctx) }, theme = 0) { init() }
}

inline fun Context.viewPager(): ViewPager = viewPager {}
inline fun Context.viewPager(init: (@AnkoViewDslMarker ViewPager).() -> Unit): ViewPager {
    return ankoView({ ctx: Context -> ViewPager(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.coordinatorLayout(): CoordinatorLayout = coordinatorLayout {}
inline fun ViewManager.coordinatorLayout(init: (@AnkoViewDslMarker _CoordinatorLayout).() -> Unit): CoordinatorLayout {
    return ankoView({ ctx: Context -> _CoordinatorLayout(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.appBarLayout(): AppBarLayout = appBarLayout {}
inline fun ViewManager.appBarLayout(init: (@AnkoViewDslMarker _AppBarLayout).() -> Unit): AppBarLayout {
    return ankoView({ ctx: Context -> _AppBarLayout(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.textInputLayout(): TextInputLayout = textInputLayout {}
inline fun ViewManager.textInputLayout(init: (@AnkoViewDslMarker _TextInputLayout).() -> Unit): TextInputLayout {
    return ankoView({ ctx: Context -> _TextInputLayout(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.themedTextInputLayout(theme: Int = 0): TextInputLayout = themedTextInputLayout(theme) {}
inline fun ViewManager.themedTextInputLayout(theme: Int = 0, init: (@AnkoViewDslMarker _TextInputLayout).() -> Unit): TextInputLayout {
    return ankoView({ ctx: Context -> _TextInputLayout(ctx) }, theme) { init() }
}

inline fun ViewManager.nestedScrollView(): NestedScrollView = nestedScrollView {}
inline fun ViewManager.nestedScrollView(init: (@AnkoViewDslMarker _NestedScrollView).() -> Unit): NestedScrollView {
    return ankoView({ ctx: Context -> _NestedScrollView(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.floatingActionButton(): FloatingActionButton = floatingActionButton {}
inline fun ViewManager.floatingActionButton(init: (@AnkoViewDslMarker FloatingActionButton).() -> Unit): FloatingActionButton {
    return ankoView({ ctx: Context -> FloatingActionButton(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.swipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout {}
inline fun ViewManager.swipeRefreshLayout(init: (@AnkoViewDslMarker SwipeRefreshLayout).() -> Unit): SwipeRefreshLayout {
    return ankoView({ ctx: Context -> SwipeRefreshLayout(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.textInputEditText(): TextInputEditText = textInputEditText {}
inline fun ViewManager.textInputEditText(init: (@AnkoViewDslMarker TextInputEditText).() -> Unit): TextInputEditText {
    return ankoView({ ctx: Context -> TextInputEditText(ctx) }, theme = 0) { init() }
}

inline fun ViewManager.themedTextInputEditText(theme: Int = 0): TextInputEditText = themedTextInputEditText(theme) {}
inline fun ViewManager.themedTextInputEditText(theme: Int = 0, init: (@AnkoViewDslMarker TextInputEditText).() -> Unit): TextInputEditText {
    return ankoView({ ctx: Context -> TextInputEditText(ctx) }, theme) { init() }
}

open class _NestedScrollView(ctx: Context) : NestedScrollView(ctx) {

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?,
        init: FrameLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = FrameLayout.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?
    ): T {
        val layoutParams = FrameLayout.LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        init: FrameLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = FrameLayout.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = FrameLayout.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        gravity: Int,
        init: FrameLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = FrameLayout.LayoutParams(width, height, gravity)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        gravity: Int
    ): T {
        val layoutParams = FrameLayout.LayoutParams(width, height, gravity)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.LayoutParams?,
        init: FrameLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = FrameLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = FrameLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?,
        init: FrameLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = FrameLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = FrameLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: FrameLayout.LayoutParams?,
        init: FrameLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = FrameLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: FrameLayout.LayoutParams?
    ): T {
        val layoutParams = FrameLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }
}

open class _TextInputLayout(ctx: Context) : TextInputLayout(ctx) {

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?,
        init: LinearLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = LinearLayout.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?
    ): T {
        val layoutParams = LinearLayout.LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        init: LinearLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = LinearLayout.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = LinearLayout.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        weight: Float,
        init: LinearLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = LinearLayout.LayoutParams(width, height, weight)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        weight: Float
    ): T {
        val layoutParams = LinearLayout.LayoutParams(width, height, weight)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        p: ViewGroup.LayoutParams?,
        init: LinearLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = LinearLayout.LayoutParams(p!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        p: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = LinearLayout.LayoutParams(p!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?,
        init: LinearLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = LinearLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = LinearLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: LinearLayout.LayoutParams?,
        init: LinearLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = LinearLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: LinearLayout.LayoutParams?
    ): T {
        val layoutParams = LinearLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }
}

open class _AppBarLayout(ctx: Context) : AppBarLayout(ctx) {

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?,
        init: AppBarLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        init: AppBarLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        weight: Float,
        init: AppBarLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(width, height, weight)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        weight: Float
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(width, height, weight)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        p: ViewGroup.LayoutParams?,
        init: AppBarLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(p!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        p: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(p!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?,
        init: AppBarLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: LinearLayout.LayoutParams?,
        init: AppBarLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: LinearLayout.LayoutParams?
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: AppBarLayout.LayoutParams?,
        init: AppBarLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: AppBarLayout.LayoutParams?
    ): T {
        val layoutParams = AppBarLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }
}

open class _CoordinatorLayout(ctx: Context) : CoordinatorLayout(ctx) {

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        init: CoordinatorLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = CoordinatorLayout.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = CoordinatorLayout.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        p: CoordinatorLayout.LayoutParams?,
        init: CoordinatorLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = CoordinatorLayout.LayoutParams(p!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        p: CoordinatorLayout.LayoutParams?
    ): T {
        val layoutParams = CoordinatorLayout.LayoutParams(p!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        p: ViewGroup.MarginLayoutParams?,
        init: CoordinatorLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = CoordinatorLayout.LayoutParams(p!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        p: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = CoordinatorLayout.LayoutParams(p!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        p: ViewGroup.LayoutParams?,
        init: CoordinatorLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = CoordinatorLayout.LayoutParams(p!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        p: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = CoordinatorLayout.LayoutParams(p!!)
        this@lparams.layoutParams = layoutParams
        return this
    }
}

open class _DrawerLayout(ctx: Context) : DrawerLayout(ctx) {

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?,
        init: DrawerLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        init: DrawerLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
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

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        gravity: Int
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(width, height, gravity)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: DrawerLayout.LayoutParams?,
        init: DrawerLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: DrawerLayout.LayoutParams?
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.LayoutParams?,
        init: DrawerLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?,
        init: DrawerLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = DrawerLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }
}

open class _RecyclerView(ctx: Context) : RecyclerView(ctx) {

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?,
        init: RecyclerView.LayoutParams.() -> Unit
    ): T {
        val layoutParams = RecyclerView.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?
    ): T {
        val layoutParams = RecyclerView.LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        init: RecyclerView.LayoutParams.() -> Unit
    ): T {
        val layoutParams = RecyclerView.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = RecyclerView.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?,
        init: RecyclerView.LayoutParams.() -> Unit
    ): T {
        val layoutParams = RecyclerView.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = RecyclerView.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.LayoutParams?,
        init: RecyclerView.LayoutParams.() -> Unit
    ): T {
        val layoutParams = RecyclerView.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = RecyclerView.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: RecyclerView.LayoutParams?,
        init: RecyclerView.LayoutParams.() -> Unit
    ): T {
        val layoutParams = RecyclerView.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: RecyclerView.LayoutParams?
    ): T {
        val layoutParams = RecyclerView.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }
}