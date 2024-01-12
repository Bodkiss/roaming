package com.knowroaming.esim.app.util.ext

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toNSDateComponents
import platform.Foundation.NSDateFormatter

actual fun LocalDateTime.formatDateTime(pattern: String): String {
    val formatter = NSDateFormatter()

    formatter.setDateFormat(pattern)

    val date = toNSDateComponents().date

    if (date !== null) {
        return formatter.stringFromDate(date)
    }

    return ""
}