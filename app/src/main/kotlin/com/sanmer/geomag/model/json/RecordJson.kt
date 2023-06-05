package com.sanmer.geomag.model.json

import com.sanmer.geomag.model.Record
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecordJson(
    val model: String,
    val time: String,
    val altitude: Double,
    val latitude: Double,
    val longitude: Double,
    @Json(name = "magnetic_field") val values: MagneticFieldJson
)

fun Record.toJson() = RecordJson(
    model = model.name,
    time = time.toString(),
    altitude = position.altitude,
    latitude = position.latitude,
    longitude = position.longitude,
    values = values.toJson()
)
