package com.maxkrass.stundenplankotlinrefactor.data

import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test


/**
 * Max made this for StundenplanKotlinRefactor on 19.11.2017.
 */
class GradeTest {

    @Test
    fun getGradeByCorrectString() {

        assertSame(Grade.EF, Grade.valueOf("EF"))
    }

    /*@Test
    fun getGradeByWrongString() {
        assertThrows<java.lang.IllegalArgumentException> { Grade.valueOf("WRONG") }
    }*/
}
