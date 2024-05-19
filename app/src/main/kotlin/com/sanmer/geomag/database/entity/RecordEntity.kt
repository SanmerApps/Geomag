package com.sanmer.geomag.database.entity

import android.location.Location
import android.location.LocationManager
import androidx.room.Embedded
import androidx.room.Entity
import com.sanmer.geomag.Compat
import com.sanmer.geomag.model.origin.DateTime
import com.sanmer.geomag.model.origin.Record

@Entity(
    tableName = "records",
    primaryKeys = ["model", "time", "altitude", "latitude", "longitude"]
)
data class RecordEntity(
    @Embedded val key: RecordKey,
    @Embedded val values: MagneticFieldEntity
) {
    constructor(r: Record) : this(
        key = RecordKey(r),
        values = MagneticFieldEntity(r.values)
    )

    fun toRecord() = Record(
        model = Compat.Models.valueOf(key.model),
        time = DateTime.parse(key.time),
        location = Location(LocationManager.GPS_PROVIDER).apply {
            latitude = key.latitude
            longitude = key.longitude
            altitude = key.altitude
        },
        values = values.toMF()
    )

}