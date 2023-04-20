package utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun formatCurrentTime(format: String) = Clock.System
    .now()
    .toLocalDateTime(TimeZone.currentSystemDefault())
    .format(format)

expect fun LocalDateTime.format(format: String): String