package com.knowroaming.esim.app.util.ext

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

actual fun LocalDateTime.formatDateTime(pattern: String): String {
    return toJavaLocalDateTime().format(DateTimeFormatter.ofPattern(pattern))
}