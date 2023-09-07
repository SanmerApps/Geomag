package com.sanmer.geomag.database.entity

import android.os.Parcelable
import androidx.room.Entity
import com.sanmer.geomag.model.Record
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "key")
data class RecordKey(
    val model: String,
    val time: String,
    val altitude: Double,
    val latitude: Double,
    val longitude: Double
) : Parcelable {
    constructor(record: Record) : this(
        model = record.model.name,
        time = record.time.toString(),
        altitude = record.position.altitude,
        latitude = record.position.latitude,
        longitude = record.position.longitude
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