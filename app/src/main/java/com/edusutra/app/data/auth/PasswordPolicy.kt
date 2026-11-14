
package com.edusutra.app.data.auth

object PasswordPolicy {

    private val commonPatterns = setOf(
        "password", "admin123", "qwerty", "letmein", "welcome",
        "123456", "12345678", "123456789", "111111", "000000",
        "abc123", "qwerty123", "password123", "edu123456"
    )

    private val symbolRegex = Regex("[^A-Za-z0-9]")

    fun validate(password: String): String? {
        if (password.length < 12) {
            return "Password must be at least 12 characters"
        }
        if (password.none { it.isUpperCase() }) {
            return "Password must contain an uppercase letter"
        }
        if (password.none { it.isLowerCase() }) {
            return "Password must contain a lowercase letter"
        }
        if (password.none { it.isDigit() }) {
            return "Password must contain a number"
        }
        if (!symbolRegex.containsMatchIn(password)) {
            return "Password must contain a special character"
        }
        val lower = password.lowercase()
        if (commonPatterns.any { lower.contains(it) }) {
            return "Password is too common. Choose a stronger password."
        }
        val hasSequentialDigits = (0..password.length - 4).any { start ->
            val window = password.substring(start, start + 4)
            window.all { it.isDigit() } &&
                (window[1].code - window[0].code == 1) &&
                (window[2].code - window[1].code == 1) &&
                (window[3].code - window[2].code == 1)
        }
        if (hasSequentialDigits) {
            return "Password must not contain sequential numbers (e.g. 1234)"
        }
        val hasRepeatedChar = password.groupBy { it }.values.any { it.size >= 4 }
        if (hasRepeatedChar) {
            return "Password must not repeat the same character 4+ times"
        }
        return null
    }

    fun isValid(password: String): Boolean = validate(password) == null
}
