package com.acme.utils

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ConsonantsTest(private val input: String, private val expected: String) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "input: {0} expected:{1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf("", ""),
                arrayOf("bbbbb", "bbbbb"),
                arrayOf("abecidofu", "bcdf"),
                arrayOf("aaabbbccc", "bbbccc"),
                arrayOf("aeiou", ""),
                arrayOf("ae!@$@!#%@%#!$%#wrwrwrw", "wrwrwrw"),
            )
        }
    }

    @Test
    fun `returns_correct_consonants`() {
        assert(input.consonants() == expected)
    }

}

