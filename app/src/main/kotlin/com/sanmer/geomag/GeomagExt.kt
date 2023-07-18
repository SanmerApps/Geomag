package com.sanmer.geomag

import com.sanmer.geomag.model.MagneticFieldExt
import com.sanmer.geomag.model.Position
import com.sanmer.geomag.model.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime

object GeomagExt {
    private fun toDecimalYears(
        year: Int, month: Int, day: Int,
        hour: Int, min: Int,
        sec: Int, nsec: Int,
    )  = Geomag.toDecimalYears(
        year.toLong(),
        month.toLong(),
        day.toLong(),
        hour.toLong(),
        min.toLong(),
        sec.toLong(),
        nsec.toLong()
    )

    private fun igrf(
        latitude: Double,
        longitude: Double,
        altKm: Double,
        decimalYears: Double
    ) = Geomag.igrf(
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
    ) = Geomag.wmm(
        latitude,
        longitude,
        altKm,
        decimalYears
    )

    fun toDecimalYears(dateTime: LocalDateTime) = toDecimalYears(
        dateTime.year, dateTime.monthNumber, dateTime.dayOfMonth,
        dateTime.hour, dateTime.minute,
        dateTime.second, dateTime.nanosecond
    )

    fun igrf(
        dataTime: LocalDateTime,
        position: Position
    ) = igrf(
        latitude = position.latitude,
        longitude = position.longitude,
        altKm = position.altitude,
        decimalYears = toDecimalYears(dataTime)
    ).let { MagneticFieldExt(it) }

    fun wmm(
        dataTime: LocalDateTime,
        position: Position
    ) = wmm(
        latitude = position.latitude,
        longitude = position.longitude,
        altKm = position.altitude,
        decimalYears = toDecimalYears(dataTime)
    ).let { MagneticFieldExt(it) }

    suspend fun run(
        model: Models,
        dataTime: LocalDateTime,
        position: Position
    ): Record = withContext(Dispatchers.Default) {
        val cal: (LocalDateTime, Position) -> MagneticFieldExt = when (model) {
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