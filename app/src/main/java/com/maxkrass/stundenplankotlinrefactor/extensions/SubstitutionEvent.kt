package com.maxkrass.stundenplankotlinrefactor.extensions

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.maxkrass.stundenplankotlinrefactor.R
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionType.*




/**
 * Max made this for StundenplanKotlinRefactor on 22.11.2017.
 */

val SubstitutionEvent.displayString: String
    get() {
        var s = "$period Std. "
        s += if (type == Special) {
            "$type $sub $newLocation"
        } else {
            "$oldTeacher $subject $type"
        }

        if (type == LocationChange) {
            s += " $newLocation"
        } else if (type == Substitution) {
            s += " $sub"
        }
        return s
    }

val SubstitutionEvent.topText: String
    get() = "$period Std. $type"

val SubstitutionEvent.secondaryText: String
    get() {

        var s = if (type == Special) "$type $sub $newLocation"
        else "$subject bei $oldTeacher"

        if (type == LocationChange) s += " $newLocation"
        else if (type == Substitution) s += " $sub"

        return s
    }

val SubstitutionEvent.iconRes: Int
    @DrawableRes
    get() = when (type) {
        Release, Cancelled        -> R.drawable.ic_cancelled_24dp
        LocationChange            -> R.drawable.ic_edit_location_24dp
        Special                   -> R.drawable.ic_star_24dp
        Substitution, ClassChange -> R.drawable.ic_warning_24dp
        None                      -> 0
    }

val SubstitutionEvent.eventColors: ColorStateList
    get() {
        @ColorInt
        val color: Int = when (type) {
            Release, Cancelled                        -> -0xbbcca
            Substitution, ClassChange, LocationChange -> -0x3c00
            Special                                   -> -0xfc560c
            else                                      -> -0x616162
        }
        return ColorStateList.valueOf(color)

    }