package com.maxkrass.stundenplankotlinrefactor.data

/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */

typealias Uid = String

interface FirebaseEntity {
    fun toMap(): Map<String, Any>
}

enum class Weekday {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY;

    override fun toString(): String {
        return name.toLowerCase()
    }

    companion object {

        fun fromString(s: String): Weekday {
            return values().firstOrNull { s == it.name.toLowerCase() }
                    ?: MONDAY
        }
    }
}
