package utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toNSDate
import platform.Foundation.NSDateFormatter

actual fun LocalDateTime.format(format: String): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = format
    return toInstant(TimeZone.currentSystemDefault()).toNSDate().let {
        dateFormatter.stringFromDate(it)
    }
}