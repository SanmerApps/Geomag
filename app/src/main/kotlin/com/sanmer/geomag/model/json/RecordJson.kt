package com.sanmer.geomag.model.json

import com.sanmer.geomag.model.origin.Record
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecordJson(
    val model: String,
    val time: String,
    val altitude: Double,
    val latitude: Double,
    val longitude: Double,
    val values: MagneticFieldJson
) {
    constructor(r: Record) : this(
        model = r.model.name,
        time = r.time.toString(),
        altitude = r.location.altitude,
        latitude = r.location.latitude,
        longitude = r.location.longitude,
        values = MagneticFieldJson(r.values)
    )
}
