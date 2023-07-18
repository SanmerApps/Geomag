package com.sanmer.geomag.database.entity

import androidx.room.ColumnInfo
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
    val model: String,
    val time: String,
    val altitude: Double,
    val latitude: Double,
    val longitude: Double,
    @Embedded val values: MagneticFieldEntity,
    @PrimaryKey val id: Double
)

val Record.primaryKey: Double get() {
    val decimal = GeomagExt.toDecimalYears(time)
    val position = position.altitude - position.latitude - position.longitude
    return decimal + position + model.ordinal
}

fun Record.toEntity() = RecordEntity(
    model = model.name,
    time = time.toString(),
    altitude = position.altitude,
    latitude = position.latitude,
    longitude = position.longitude,
    values = values.toEntity(),
    id = primaryKey
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

@Entity(tableName = "magnetic_field")
data class MagneticFieldEntity(
    @ColumnInfo(name = "declination") val declination: Double,
    @ColumnInfo(name = "declination_sv") val declinationSV: Double,
    @ColumnInfo(name = "inclination") val inclination: Double,
    @ColumnInfo(name = "inclination_sv") val inclinationSV: Double,
    @ColumnInfo(name = "horizontal_intensity") val horizontalIntensity: Double,
    @ColumnInfo(name = "horizontal_sv") val horizontalSV: Double,
    @ColumnInfo(name = "north_component") val northComponent: Double,
    @ColumnInfo(name = "north_sv") val northSV: Double,
    @ColumnInfo(name = "east_component") val eastComponent: Double,
    @ColumnInfo(name = "east_sv") val eastSV: Double,
    @ColumnInfo(name = "vertical_component") val verticalComponent: Double,
    @ColumnInfo(name = "vertical_sv") val verticalSV: Double,
    @ColumnInfo(name = "total_intensity") val totalIntensity: Double,
    @ColumnInfo(name = "total_sv") val totalSV: Double
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
