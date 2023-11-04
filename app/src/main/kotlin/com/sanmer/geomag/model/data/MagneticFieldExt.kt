package com.sanmer.geomag.model.data

import dev.sanmer.geomag.MagneticField

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
        declination = field.d,
        declinationSV = field.dDot,
        inclination = field.i,
        inclinationSV = field.iDot,
        horizontalIntensity = field.h,
        horizontalSV = field.hDot,
        northComponent = field.x,
        northSV = field.xDot,
        eastComponent = field.y,
        eastSV = field.yDot,
        verticalComponent = field.z,
        verticalSV = field.zDot,
        totalIntensity = field.f,
        totalSV = field.fDot
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