package com.maxkrass.typesaferecyclerview

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Convenience ViewHolder class for list items that need a reference to their [Host] to dispatch
 * UI interaction events on, and use the same [Data] for all items.
 */
abstract class ItemViewHolder<Data : Any, V : View, Host>(protected val host: Host, itemView: V)
    : ViewHolder<V>(itemView) {

    /**
     * This property is updated from [bind] before [V.onBind] is called.
     */
    lateinit var data: Data
        private set

    constructor(host: Host, @LayoutRes layoutResId: Int, parent: ViewGroup)
            : this(host, parent.inflateItem(layoutResId))

    /**
     * Don't create objects, use non inlined lambdas, or call methods doing so in this callback
     * as it may be called a lot of times as the user scrolls faster and faster, and allocating
     * memory could affect the UI smoothness.
     * @see data
     * @see bind
     */
    protected abstract fun V.onBind()

    /** To be called from your [RecyclerView.Adapter.onBindViewHolder] implementation. */
    fun bind(newData: Data) {
        data = newData
        itemView.onBind()
    }
}
