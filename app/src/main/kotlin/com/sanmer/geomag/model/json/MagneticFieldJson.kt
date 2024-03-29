package com.sanmer.geomag.model.json

import com.squareup.moshi.JsonClass
import dev.sanmer.geomag.MagneticField

@JsonClass(generateAdapter = true)
data class MagneticFieldJson(
    val x: Double,
    val xDot: Double,
    val y: Double,
    val yDot: Double,
    val z: Double,
    val zDot: Double,
    val h: Double,
    val hDot: Double,
    val f: Double,
    val fDot: Double,
    val d: Double,
    val dDot: Double,
    val i: Double,
    val iDot: Double
) {
    constructor(m: MagneticField) : this(
        x = m.x, xDot = m.xDot,
        y = m.y, yDot = m.yDot,
        z = m.z, zDot = m.zDot,
        h = m.h, hDot = m.hDot,
        f = m.f, fDot = m.fDot,
        d = m.d, dDot = m.dDot,
        i = m.i, iDot = m.iDot
    )
}
