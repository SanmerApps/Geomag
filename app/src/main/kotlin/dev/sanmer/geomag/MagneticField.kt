package dev.sanmer.geomag

data class MagneticField(
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
)
