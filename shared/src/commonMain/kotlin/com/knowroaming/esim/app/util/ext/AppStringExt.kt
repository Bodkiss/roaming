package com.knowroaming.esim.app.util.ext

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun String.isValidEmail(): Boolean {
    return Regex("^\\S+@\\S+\\.\\S+\$").matches(this)
}


fun String.isStrongPassword(): Boolean {
    return Regex(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}\$"
    ).matches(this)
}

fun Char.isPhoneNumberChar(): Boolean {
    return Regex("[0-9+( )-]").matches(this.toString())
}

fun String.isInternationalPhoneNumber(): Boolean {
    return Regex("^(?:\\(?(?:00|\\+)([1-4]\\d\\d|[1-9]\\d*)\\)?[\\-. \\\\/]?)?((?:\\(?\\d+\\)?[\\-. \\\\/]?)*)(?:[\\-. \\\\/]?(?:#|ext\\.?|extension|x)[\\-. \\\\/]?(\\d+))?\$").matches(
        this
    )
}


fun String.parseDateTimeString(): LocalDateTime? {
    try {
        return Instant.parse(this).toLocalDateTime(TimeZone.UTC)
    } catch (e: Exception) {
        println("Error parsing DateTime string: $e")
    }

    return null
}

expect fun LocalDateTime.formatDateTime(pattern: String = "yyyy-MM-dd HH:mm:ss"): String

fun LocalDateTime?.tryFormatDateTime(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    if (this == null) {
        return ""
    }

    return this.formatDateTime(pattern)
}