package com.acme.utils

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class StringUtilsTest(private val input: String, private val expected: String) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "input: {0} expected:{1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf("", ""),
                arrayOf("bbbbb", ""),
                arrayOf("abecidofu", "aeiou"),
                arrayOf("aaabbbccc", "aaa"),
                arrayOf("aeiou", "aeiou"),
                arrayOf("ae!@$@!#%@%#!$%#wrwrwrw", "ae"),
            )
        }
    }

    @Test
    @Parameterized.Parameters()
    fun `returns_correct_vowels`() {
        assert(input.vowels() == expected)
    }
}