package com.sanmer.geomag.database.entity

import androidx.room.Entity
import com.sanmer.geomag.model.origin.Record

@Entity(tableName = "key")
data class RecordKey(
    val model: String,
    val time: String,
    val altitude: Double,
    val latitude: Double,
    val longitude: Double
) {
    constructor(r: Record) : this(
        model = r.model.name,
        time = r.time.toString(),
        altitude = r.location.altitude,
        latitude = r.location.latitude,
        longitude = r.location.longitude
    )

    override fun toString(): String {
        return "$model, $time, $altitude, $latitude, $longitude"
    }

    companion object {
        fun parse(str: String): RecordKey {
            val values = str.split(", ", limit=5)
            return RecordKey(
                model = values[0],
                time = values[1],
                altitude = values[2].toDouble(),
                latitude = values[3].toDouble(),
                longitude = values[4].toDouble()
            )
        }
    }
}