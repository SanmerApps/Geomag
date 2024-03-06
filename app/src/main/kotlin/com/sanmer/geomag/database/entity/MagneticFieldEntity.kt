package com.sanmer.geomag.database.entity

import androidx.room.Entity
import dev.sanmer.geomag.MagneticField

@Entity(tableName = "values")
data class MagneticFieldEntity(
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

    fun toMF() = MagneticField(
        x = x, xDot = xDot,
        y = y, yDot = yDot,
        z = z, zDot = zDot,
        h = h, hDot = hDot,
        f = f, fDot = fDot,
        d = d, dDot = dDot,
        i = i, iDot = iDot
    )
}