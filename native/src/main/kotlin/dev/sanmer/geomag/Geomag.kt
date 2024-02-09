package dev.sanmer.geomag

import kotlinx.datetime.LocalDateTime

object Geomag {
    init {
        ImplLibrary.load()
    }

    external fun toDecimalYears(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Double
    external fun wmm(longitude: Double, latitude: Double, altitude: Double, decimalYears: Double): MagneticField
    external fun igrf(longitude: Double, latitude: Double, altitude: Double, decimalYears: Double): MagneticField

    fun toDecimalYears(date: LocalDateTime) =
        toDecimalYears(date.year, date.monthNumber, date.dayOfMonth, date.hour, date.minute, date.second)

    fun wmm(longitude: Double, latitude: Double, altitude: Double, date: LocalDateTime) =
        wmm(longitude, latitude, altitude, toDecimalYears(date))

    fun igrf(longitude: Double, latitude: Double, altitude: Double, date: LocalDateTime) =
        igrf(longitude, latitude, altitude, toDecimalYears(date))
}