package com.sanmer.geomag.model

import android.location.Location

class Position(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double
) {
    constructor(location: Location) : this(
        latitude = location.latitude,
        longitude = location.longitude,
        altitude = location.altitude / 1000.0
    )

    val latitudeWithUnit = "${latitude}º N"
    val longitudeWithUnit = "${longitude}º W"
    val altitudeWithUnit = "$altitude km"

    override fun toString(): String {
        return "${latitude}º N ${longitude}º W $altitude km"
    }

    companion object {
        fun empty() = Position(0.0, 0.0, 0.0)
    }
}