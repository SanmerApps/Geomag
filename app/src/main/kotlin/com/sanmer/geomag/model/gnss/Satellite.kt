package com.sanmer.geomag.model.gnss

import androidx.core.location.GnssStatusCompat
import com.sanmer.geomag.model.gnss.GnssType.Companion.code
import com.sanmer.geomag.model.gnss.GnssType.Companion.toGnssType
import com.sanmer.geomag.model.gnss.SbasType.Companion.code
import com.sanmer.geomag.model.gnss.SbasType.Companion.toSbasType

data class Satellite(
    val id: Int,
    val type: Int,
    val cn0: Float,
    val elevation: Float,
    val azimuth: Float
) {
    val gnssType = type.toGnssType()

    val isSbas = gnssType == GnssType.SBAS
    val sbasType = id.toSbasType()

    val code get() = if (isSbas) {
        sbasType.code
    } else {
        gnssType.code
    }

    val name get() = if (isSbas) {
        sbasType.name
    } else {
        gnssType.name
    }


    override fun toString(): String {
        return "ID: $id, C/N0: $cn0, Elev: ${elevation}º, Azim: ${azimuth}º"
    }

    constructor(status: GnssStatusCompat, index: Int) : this(
        id = status.getSvid(index),
        type = status.getConstellationType(index),
        cn0 = status.getCn0DbHz(index),
        elevation = status.getElevationDegrees(index),
        azimuth = status.getAzimuthDegrees(index)
    )
}