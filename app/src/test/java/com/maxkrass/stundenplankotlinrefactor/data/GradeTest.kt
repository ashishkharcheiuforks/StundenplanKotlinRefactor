package com.maxkrass.stundenplankotlinrefactor.data

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Max made this for StundenplanKotlinRefactor on 19.11.2017.
 */
class GradeTest {

    @Test
    fun getGradeByCorrectString() {

        assertThat(Grade.valueOf("EF"), `is`(Grade.EF))

    }

    @Test(expected = IllegalArgumentException::class)
    fun getGradeByWrongString() {

        Grade.valueOf("WRONG")

    }

}