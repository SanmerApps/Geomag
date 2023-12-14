package com.sanmer.geomag.model.gnss

import androidx.core.location.GnssStatusCompat
import com.sanmer.geomag.model.gnss.GnssType.Companion.code
import com.sanmer.geomag.model.gnss.GnssType.Companion.toGnssType
import com.sanmer.geomag.model.gnss.SbasType.Companion.code
import com.sanmer.geomag.model.gnss.SbasType.Companion.toSbasType

data class Satellite(
    val svid: Int,
    val type: Int,
    val cn0: Float,
    val elevation: Float,
    val azimuth: Float,
    val carrierFrequency: Float,
) {
    val gnssType = type.toGnssType()

    val isSbas = gnssType == GnssType.SBAS
    val sbasType = svid.toSbasType()

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

    constructor(status: GnssStatusCompat, index: Int) : this(
        svid = status.getSvid(index),
        type = status.getConstellationType(index),
        cn0 = status.getCn0DbHz(index),
        elevation = status.getElevationDegrees(index),
        azimuth = status.getAzimuthDegrees(index),
        carrierFrequency = status.getCarrierFrequencyHz(index) / 1000000.00f
    )
}