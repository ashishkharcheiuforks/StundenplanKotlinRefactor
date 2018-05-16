package com.maxkrass.stundenplankotlinrefactor.data

import java.io.Serializable
import java.security.InvalidParameterException

/**
 * Max made this for StundenplanKotlinRefactor on 08.09.2017.
 */
typealias TeacherSubjects = HashMap<SubjectName, Boolean>
typealias TeacherName = String
typealias TeacherContraction = String

data class Teacher(
        val name: TeacherName,
        val contraction: TeacherContraction,
        val subjects: TeacherSubjects) : Serializable, Comparable<Teacher> {

    init {
        if (contraction.isEmpty())
            throw InvalidParameterException("Teacher Contraction mustn't be empty!")

        if (contraction.length > 3)
            throw InvalidParameterException("Teacher Contraction mustn't be longer than 3 characters")
    }

    override fun compareTo(other: Teacher): Int =
            name.compareTo(other = other.name)

}
