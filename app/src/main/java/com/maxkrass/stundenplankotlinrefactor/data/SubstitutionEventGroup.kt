package com.maxkrass.stundenplankotlinrefactor.data

/**
 * Max made this for Stundenplan2 on 23.04.2017.
 */

data class SubstitutionEventGroup(val headerText: String, val events: MutableList<SubstitutionEvent> = mutableListOf())