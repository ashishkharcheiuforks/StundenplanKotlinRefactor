/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maxkrass.typesaferecyclerview

/**
 * Type-safe version of [RecyclerView.ViewHolder].
 */
open class ViewHolder<V : View>(val itemView: V) : RecyclerView.ViewHolder(itemView) {

    /**
     * Creates a ViewHolder from a layout resource id.
     *
     * @param layoutResId ID for an XML layout resource to load (e.g., R.layout.main_page)
     * @param parent pass the first argument received in
     * [RecyclerView.Adapter.onCreateViewHolder]
     */
    constructor(@LayoutRes layoutResId: Int, parent: ViewGroup) : this(parent.inflateItem<V>(
            layoutResId)) {
    }
}
