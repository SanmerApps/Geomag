package com.sanmer.geomag

import com.sanmer.geomag.model.MagneticField
import com.sanmer.geomag.model.Position
import com.sanmer.geomag.model.Record
import com.sanmer.geomag.model.toField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import go.geomag.Geomag as GoGeomag

object Geomag {
    private fun toDecimalYears(
        year: Int, month: Int, day: Int,
        hour: Int, min: Int, sec: Int
    )  = GoGeomag.toDecimalYears(
        year.toLong(),
        month.toLong(),
        day.toLong(),
        hour.toLong(),
        min.toLong(),
        sec.toLong(),
        0L
    )

    private fun igrf(
        latitude: Double,
        longitude: Double,
        altKm: Double,
        decimalYears: Double
    ) = GoGeomag.igrf(
        latitude,
        longitude,
        altKm,
        decimalYears
    )

    private fun wmm(
        latitude: Double,
        longitude: Double,
        altKm: Double,
        decimalYears: Double
    ) = GoGeomag.wmm(
        latitude,
        longitude,
        altKm,
        decimalYears
    )

    fun toDecimalYears(dateTime: LocalDateTime) = toDecimalYears(
        dateTime.year, dateTime.monthNumber, dateTime.dayOfMonth,
        dateTime.hour, dateTime.minute, dateTime.second
    )

    fun igrf(
        dataTime: LocalDateTime,
        position: Position
    ) = igrf(
        latitude = position.latitude,
        longitude = position.longitude,
        altKm = position.altitude,
        decimalYears = toDecimalYears(dataTime)
    ).toField()

    fun wmm(
        dataTime: LocalDateTime,
        position: Position
    ) = wmm(
        latitude = position.latitude,
        longitude = position.longitude,
        altKm = position.altitude,
        decimalYears = toDecimalYears(dataTime)
    ).toField()

    suspend fun run(
        model: Models,
        dataTime: LocalDateTime,
        position: Position
    ): Record = withContext(Dispatchers.Default) {
        val cal: (LocalDateTime, Position) -> MagneticField = when (model) {
            Models.IGRF -> ::igrf
            Models.WMM -> ::wmm
        }

        return@withContext Record(
            model = model,
            time = dataTime,
            position = position,
            values = cal(dataTime, position)
        )
    }

    enum class Models {
        IGRF,
        WMM
    }
}