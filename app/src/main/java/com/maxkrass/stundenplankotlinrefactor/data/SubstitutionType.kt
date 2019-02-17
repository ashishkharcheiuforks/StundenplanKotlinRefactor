package com.maxkrass.stundenplankotlinrefactor.data

enum class SubstitutionType(private val type: String = "") {
    Cancelled("fällt aus"),
    Substitution("Vertr."),
    ClassChange("Unter.-Änd."),
    LocationChange("Raum-Änd."),
    Special("Sond"),
    Release("Freisetzung"),
    None();

    override fun toString(): String = type

    companion object {

        fun searchTypebyString(s: String): SubstitutionType {
            SubstitutionType.values().first { it.type == s }
            return None
        }
    }
}