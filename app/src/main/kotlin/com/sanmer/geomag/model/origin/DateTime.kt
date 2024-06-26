package com.sanmer.geomag.model.origin

import com.sanmer.geomag.Compat
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
    constructor(dt: LocalDateTime) : this(
        year = dt.year,
        monthNumber = dt.monthNumber,
        dayOfMonth = dt.dayOfMonth,
        hour = dt.hour,
        minute = dt.minute,
        second = dt.second
    )

    val local = LocalDateTime(year, monthNumber, dayOfMonth, hour, minute, second)
    val decimalOfLocal get() = Compat.toDecimalYears(local)
    private val utc get() = local.toTimeZone(TimeZone.UTC)
    val decimalOfUtc get() = Compat.toDecimalYears(utc)

    override fun toString(): String {
        return local.toString()
    }

    companion object {
        fun now(): DateTime {
            val dt = LocalDateTime.now()
            return DateTime(dt)
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