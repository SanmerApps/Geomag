package com.sanmer.geomag.model.gnss

import androidx.core.location.GnssStatusCompat

enum class GnssType {
    NAVSTAR,
    GLONASS,
    BEIDOU,
    QZSS,
    GALILEO,
    IRNSS,
    SBAS,
    UNKNOWN;
    
    companion object {
        val GnssType.code: String get() {
            return when (this) {
                NAVSTAR -> "US"
                GLONASS -> "RU"
                BEIDOU -> "CN"
                QZSS -> "JP"
                GALILEO -> "EU"
                IRNSS -> "IN"
                SBAS -> ""
                UNKNOWN -> "XX"
            }
        }

        fun Int.toGnssType(): GnssType {
            return when (this) {
                GnssStatusCompat.CONSTELLATION_GPS -> NAVSTAR
                GnssStatusCompat.CONSTELLATION_GLONASS -> GLONASS
                GnssStatusCompat.CONSTELLATION_BEIDOU -> BEIDOU
                GnssStatusCompat.CONSTELLATION_QZSS -> QZSS
                GnssStatusCompat.CONSTELLATION_GALILEO -> GALILEO
                GnssStatusCompat.CONSTELLATION_IRNSS -> IRNSS
                GnssStatusCompat.CONSTELLATION_SBAS -> SBAS
                else -> UNKNOWN
            }
        }
    }
}