package com.sanmer.geomag.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sanmer.geomag.GeomagExt
import com.sanmer.geomag.model.MagneticFieldExt
import com.sanmer.geomag.model.Position
import com.sanmer.geomag.model.Record
import kotlinx.datetime.toLocalDateTime

@Entity(tableName = "records")
data class RecordEntity(
    @PrimaryKey val id: Double,
    val model: String,
    val time: String,
    val altitude: Double,
    val latitude: Double,
    val longitude: Double,
    @Embedded val values: MagneticFieldEntity
)

val Record.primaryKey: Double get() {
    val decimal = GeomagExt.toDecimalYears(time)
    val position = position.altitude - position.latitude - position.longitude
    return decimal + position + model.ordinal
}

fun Record.toEntity() = RecordEntity(
    id = primaryKey,
    model = model.name,
    time = time.toString(),
    altitude = position.altitude,
    latitude = position.latitude,
    longitude = position.longitude,
    values = values.toEntity(),
)

fun RecordEntity.toRecord() = Record(
    model = GeomagExt.Models.valueOf(model),
    time = time.toLocalDateTime(),
    position = Position(
        latitude = latitude,
        longitude = longitude,
        altitude = altitude
    ),
    values = values.toMF()
)

@Entity(tableName = "values")
data class MagneticFieldEntity(
    val declination: Double,
    val declinationSV: Double,
    val inclination: Double,
    val inclinationSV: Double,
    val horizontalIntensity: Double,
    val horizontalSV: Double,
    val northComponent: Double,
    val northSV: Double,
    val eastComponent: Double,
    val eastSV: Double,
    val verticalComponent: Double,
    val verticalSV: Double,
    val totalIntensity: Double,
    val totalSV: Double
)

fun MagneticFieldExt.toEntity() = MagneticFieldEntity(
    declination, declinationSV,
    inclination, inclinationSV,
    horizontalIntensity, horizontalSV,
    northComponent, northSV,
    eastComponent, eastSV,
    verticalComponent, verticalSV,
    totalIntensity, totalSV
)

fun MagneticFieldEntity.toMF() = MagneticFieldExt(
    declination, declinationSV,
    inclination, inclinationSV,
    horizontalIntensity, horizontalSV,
    northComponent, northSV,
    eastComponent, eastSV,
    verticalComponent, verticalSV,
    totalIntensity, totalSV
)
