package com.sanmer.geomag.model.data

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
    val decimalOfLocal get() = GeomagExt.toDecimalYears(local)
    private val utc get() = local.toTimeZone(TimeZone.UTC)
    val decimalOfUtc get() = GeomagExt.toDecimalYears(utc)

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