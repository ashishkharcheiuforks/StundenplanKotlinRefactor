package com.maxkrass.stundenplankotlinrefactor.data

import android.graphics.Color
import java.io.Serializable
import java.security.InvalidParameterException

/**
 * Max made this for StundenplanKotlinRefactor on 08.09.2017.
 */
typealias Subjects = MutableMap<SubjectName, Subject>
typealias SubjectName = String
typealias SubjectAbbreviation = String
typealias SubjectColor = String

data class Subject(
    var name: SubjectName,
    var abbreviation: SubjectAbbreviation,
    var teacher: TeacherContraction,
    var color: SubjectColor
) : Serializable, Comparable<Subject>, FirebaseEntity {

    constructor() : this(
            name = "EMPTY SUBJECT",
            abbreviation = "ETSBJ",
            teacher = "",
            color = "#FF0000")

    val colorInt: Int get() = Color.parseColor(color)

    init {
        if (name.isBlank())
            throw InvalidParameterException("Subject Name mustn't be empty!")
        if (abbreviation.length > 5)
            throw InvalidParameterException("Subject Abbreviation mustn't be longer than 5 characters")
        if (!color.startsWith('#', true) || color.length != 7)
            throw InvalidParameterException("Subject Color must start have the #XXXXXX pattern, instead is $color")
    }

    override fun toMap(): Map<String, Any> = mapOf(
            "name" to name,
            "abbreviation" to abbreviation,
            "teacher" to teacher,
            "color" to color)

    override fun compareTo(other: Subject): Int =
            name.compareTo(other.name)
}
