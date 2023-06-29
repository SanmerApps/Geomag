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
    declinationSV = declinationSV,
    inclination = inclination,
    inclinationSV = inclinationSV,
    horizontalIntensity = horizontalIntensity,
    horizontalSV = horizontalSV,
    northComponent = northComponent,
    northSV = northSV,
    eastComponent = eastComponent,
    eastSV = eastSV,
    verticalComponent = verticalComponent,
    verticalSV = verticalSV,
    totalIntensity = totalIntensity,
    totalSV = totalSV
)
