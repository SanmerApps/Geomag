package com.sanmer.geomag.model.json

import com.sanmer.geomag.model.MagneticFieldExt
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MagneticFieldJson(
    @Json(name = "declination") val declination: Double,
    @Json(name = "declination_sv") val declinationSV: Double,
    @Json(name = "inclination") val inclination: Double,
    @Json(name = "inclination_sv") val inclinationSV: Double,
    @Json(name = "horizontal_intensity") val horizontalIntensity: Double,
    @Json(name = "horizontal_sv") val horizontalSV: Double,
    @Json(name = "north_component") val northComponent: Double,
    @Json(name = "north_sv") val northSV: Double,
    @Json(name = "east_component") val eastComponent: Double,
    @Json(name = "east_sv") val eastSV: Double,
    @Json(name = "vertical_component") val verticalComponent: Double,
    @Json(name = "vertical_sv") val verticalSV: Double,
    @Json(name = "total_intensity") val totalIntensity: Double,
    @Json(name = "total_sv") val totalSV: Double,
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
