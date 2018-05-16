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

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Made to inflate itemViews for usage in a [RecyclerView].
 */
fun <V : View> ViewGroup.inflateItem(@LayoutRes resId: Int): V {
    @Suppress("UNCHECKED_CAST")
    return LayoutInflater.from(context).inflate(resId, this, false) as V
}

/**
 * @param attachToRoot should be explicitly false if used for a [RecyclerView] item, but the
 * default (true) can left as is for most other cases.
 *
 * @see LayoutInflater.inflate
 */
fun ViewGroup.inflate(@LayoutRes resId: Int, attachToRoot: Boolean = true): View {
    @Suppress("UNCHECKED_CAST")
    return LayoutInflater.from(context).inflate(resId, this, attachToRoot)
}
