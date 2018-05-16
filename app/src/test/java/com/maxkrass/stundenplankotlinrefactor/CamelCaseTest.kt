package com.maxkrass.stundenplankotlinrefactor

import com.maxkrass.stundenplankotlinrefactor.extensions.toCamelCase
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Max made this for StundenplanKotlinRefactor on 05.09.2017.
 */
class CamelCaseTest {

    @Test
    @Throws(Exception::class)
    fun camelizationIsCorrect() {
        assertEquals("ManageSubjectsFragment", "manage_subjects_fragment".toCamelCase())
    }
}