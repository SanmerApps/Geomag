package com.sanmer.geomag

import com.sanmer.geomag.model.MagneticFieldExt
import com.sanmer.geomag.model.Position
import dev.sanmer.geomag.Geomag

object GeomagExt {
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