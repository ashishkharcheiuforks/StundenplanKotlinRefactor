package com.maxkrass.stundenplankotlinrefactor.substitution

import com.maxkrass.stundenplankotlinrefactor.data.Grade
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEvent
import com.maxkrass.stundenplankotlinrefactor.data.SubstitutionEventGroup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SubstitutionEventGroupUtilsTest {

    @Test
    fun test_convertEventList() {

        val events = listOf(
                SubstitutionEvent(grade = Grade.Q1, subject = "Mathematik"),
                SubstitutionEvent(grade = Grade.Q2, subject = "Deutsch"),
                SubstitutionEvent(grade = Grade.EF, subject = "Sport"),
                SubstitutionEvent(grade = Grade.Q2, subject = "Mathematik"),
                SubstitutionEvent(grade = Grade.Q1, subject = "Deutsch"),
                SubstitutionEvent(grade = Grade.EF, subject = "Geschichte")
        )

        val actualList = SubstitutionEventGroupUtils.convertEventList(events, emptyMap())

        val expectedList = setOf(
                SubstitutionEventGroup("Meine Fächer", mutableListOf()),
                SubstitutionEventGroup("EF", mutableListOf(
                        SubstitutionEvent(grade = Grade.EF, subject = "Sport"),
                        SubstitutionEvent(grade = Grade.EF, subject = "Geschichte"))),
                SubstitutionEventGroup("Q1", mutableListOf(
                        SubstitutionEvent(grade = Grade.Q1, subject = "Mathematik"),
                        SubstitutionEvent(grade = Grade.Q1, subject = "Deutsch"))),
                SubstitutionEventGroup("Q2", mutableListOf(
                        SubstitutionEvent(grade = Grade.Q2, subject = "Deutsch"),
                        SubstitutionEvent(grade = Grade.Q2, subject = "Mathematik"))))

        assertEquals(expectedList, actualList)

    }

}