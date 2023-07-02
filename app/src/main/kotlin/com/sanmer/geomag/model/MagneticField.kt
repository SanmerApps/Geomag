package com.sanmer.geomag.model

import go.geomag.MagneticField as GoMagneticField

data class MagneticField(
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
    val totalSV: Double
) {
    companion object {
        fun empty() = MagneticField(
            0.0, 0.0,
            0.0, 0.0,
            0.0, 0.0,
            0.0, 0.0,
            0.0, 0.0,
            0.0, 0.0,
            0.0, 0.0
        )
    }
}

fun GoMagneticField.toField() = MagneticField(
    declination = declination,
    declinationSV = declinationSV.orZero(),
    inclination = inclination,
    inclinationSV = inclinationSV.orZero(),
    horizontalIntensity = horizontalIntensity,
    horizontalSV = horizontalSV.orZero(),
    northComponent = northComponent,
    northSV = northSV.orZero(),
    eastComponent = eastComponent,
    eastSV = eastSV.orZero(),
    verticalComponent = verticalComponent,
    verticalSV = verticalSV.orZero(),
    totalIntensity = totalIntensity,
    totalSV = totalSV.orZero()
)

private fun Double.orZero() = if (isNaN()) 0.0 else this
