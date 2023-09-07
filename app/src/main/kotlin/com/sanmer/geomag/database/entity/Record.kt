package com.sanmer.geomag.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import com.sanmer.geomag.GeomagExt
import com.sanmer.geomag.model.Position
import com.sanmer.geomag.model.Record
import kotlinx.datetime.toLocalDateTime

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
    time = key.time.toLocalDateTime(),
    position = Position(
        latitude = key.latitude,
        longitude = key.longitude,
        altitude = key.altitude
    ),
    values = values.toMF()
)
