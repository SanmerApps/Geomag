package com.sanmer.geomag

import android.location.Location
import dev.sanmer.geomag.Geomag
import kotlinx.datetime.LocalDateTime

object GeomagExt {
    fun toDecimalYears(date: LocalDateTime) = Geomag.toDecimalYears(date)

    fun single(
        model: Models,
        location: Location,
        decimalYears: Double
    ) = when (model) {
        Models.IGRF -> {
            Geomag.igrf(
                longitude = location.longitude,
                latitude = location.latitude,
                altitude = location.altitude,
                decimalYears = decimalYears
            )
        }
        Models.WMM -> {
            Geomag.wmm(
                longitude = location.longitude,
                latitude = location.latitude,
                altitude = location.altitude,
                decimalYears = decimalYears
            )
        }
    }

    enum class Models {
        IGRF,
        WMM
    }
}