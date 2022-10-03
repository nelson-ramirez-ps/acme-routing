package com.acme.utils

/**
 * Provides utility functions for strings, like getting just vowels and just consonants
 */
val leaveVowels = "[^aeiou]".toRegex()
val leaveConsonants = "[^bcdfghjklmnpqrstvwxyz]".toRegex()
fun String.vowels(): String = this.lowercase().replace(leaveVowels, "")
fun String.consonants(): String = this.lowercase().replace(leaveConsonants, "")
fun String.isLengthEven() = this.length % 2 == 0