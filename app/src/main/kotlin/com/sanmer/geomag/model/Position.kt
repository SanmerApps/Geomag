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

    val latitudeWithUnit get() = "${latitude}º N"
    val longitudeWithUnit get() = "${longitude}º W"
    val altitudeWithUnit get() = "$altitude km"

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Location -> {
                altitude == other.altitude
                && latitude == other.latitude
                && longitude == other.longitude
            }
            is Position -> {
                altitude == other.altitude
                && latitude == other.latitude
                && longitude == other.longitude
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = altitude.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        return result
    }

    override fun toString(): String {
        return "${latitude}º N ${longitude}º W $altitude km"
    }

    companion object {
        fun empty() = Position(0.0, 0.0, 0.0)
    }
}