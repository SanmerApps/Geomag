package com.sanmer.geomag.model

import com.sanmer.geomag.MagneticField

data class MagneticFieldExt(
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
    constructor(field: MagneticField) : this(
        declination = field.declination,
        declinationSV = field.declinationSV.orZero(),
        inclination = field.inclination,
        inclinationSV = field.inclinationSV.orZero(),
        horizontalIntensity = field.horizontalIntensity,
        horizontalSV = field.horizontalSV.orZero(),
        northComponent = field.northComponent,
        northSV = field.northSV.orZero(),
        eastComponent = field.eastComponent,
        eastSV = field.eastSV.orZero(),
        verticalComponent = field.verticalComponent,
        verticalSV = field.verticalSV.orZero(),
        totalIntensity = field.totalIntensity,
        totalSV = field.totalSV.orZero()
    )

    companion object {
        fun empty() = MagneticFieldExt(
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

private fun Double.orZero() = if (isNaN()) 0.0 else this
