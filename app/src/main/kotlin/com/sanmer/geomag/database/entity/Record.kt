package com.sanmer.geomag.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import com.sanmer.geomag.GeomagExt
import com.sanmer.geomag.model.data.DateTime
import com.sanmer.geomag.model.data.Position
import com.sanmer.geomag.model.data.Record

@Entity(
    tableName = "records",
    primaryKeys = ["model", "time", "altitude", "latitude", "longitude"]
)
data class RecordEntity(
    @Embedded val key: RecordKey,
    @Embedded val values: MagneticFieldEntity
)

fun Record.toEntity() = RecordEntity(
    key = RecordKey(this),
    values = values.toEntity(),
)

fun RecordEntity.toRecord() = Record(
    model = GeomagExt.Models.valueOf(key.model),
    time = DateTime.parse(key.time),
    position = Position(
        latitude = key.latitude,
        longitude = key.longitude,
        altitude = key.altitude
    ),
    values = values.toMF()
)
