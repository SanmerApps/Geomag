package com.sanmer.geomag

import com.sanmer.geomag.model.data.MagneticFieldExt
import com.sanmer.geomag.model.data.Position
import dev.sanmer.geomag.Geomag
import kotlinx.datetime.LocalDateTime

object GeomagExt {
    fun toDecimalYears(date: LocalDateTime) = Geomag.toDecimalYears(date)

    fun igrf(
        position: Position,
        decimalYears: Double
    ) = Geomag.igrf(
        latitude = position.latitude,
        longitude = position.longitude,
        altitude = position.altitude,
        decimalYears = decimalYears
    ).let { MagneticFieldExt(it) }

    fun wmm(
        position: Position,
        decimalYears: Double
    ) = Geomag.wmm(
        latitude = position.latitude,
        longitude = position.longitude,
        altitude = position.altitude,
        decimalYears = decimalYears
    ).let { MagneticFieldExt(it) }

    enum class Models {
        IGRF,
        WMM
    }
}