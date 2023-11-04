package com.sanmer.geomag.model.json

import com.sanmer.geomag.model.data.MagneticFieldExt
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MagneticFieldJson(
    val declination: Double,
    val declinationSV: Double,
    val inclination: Double,
    val inclinationSV: Double,
    val horizontalIntensity: Double,
    val horizontalSV: Double,
    val northComponent: Double,
    val northSV: Double,
    val eastComponent: Double,
    val eastSV: Double,
    val verticalComponent: Double,
    val verticalSV: Double,
    val totalIntensity: Double,
    val totalSV: Double,
)

fun MagneticFieldExt.toJson() = MagneticFieldJson(
    declination, declinationSV,
    inclination, inclinationSV,
    horizontalIntensity, horizontalSV,
    northComponent, northSV,
    eastComponent, eastSV,
    verticalComponent, verticalSV,
    totalIntensity, totalSV
)
