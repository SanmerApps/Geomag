package com.sanmer.geomag.model

import com.sanmer.geomag.GeomagExt
import com.sanmer.geomag.utils.extensions.now
import com.sanmer.geomag.utils.extensions.toTimeZone
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone

class DateTime private constructor(
    year: Int,
    monthNumber: Int,
    dayOfMonth: Int,
    hour: Int,
    minute: Int,
    second: Int
) {
    val local = LocalDateTime(year, monthNumber, dayOfMonth, hour, minute, second)
    val utc = local.toTimeZone(TimeZone.UTC)
    val decimalOfUtc = GeomagExt.toDecimalYears(utc)

    override fun toString(): String {
        return local.toString()
    }

    companion object {
        fun now(): DateTime {
            val t = LocalDateTime.now()
            return DateTime(
                t.year,
                t.monthNumber,
                t.dayOfMonth,
                t.hour,
                t.minute,
                t.second
            )
        }

        fun parse(isoString: String): DateTime {
            val t = LocalDateTime.parse(isoString)
            return DateTime(
                t.year,
                t.monthNumber,
                t.dayOfMonth,
                t.hour,
                t.minute,
                t.second
            )
        }
    }
}