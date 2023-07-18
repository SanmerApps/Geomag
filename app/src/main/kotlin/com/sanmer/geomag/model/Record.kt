package com.sanmer.geomag.model

import com.sanmer.geomag.GeomagExt
import com.sanmer.geomag.utils.expansion.now
import com.squareup.moshi.JsonClass
import kotlinx.datetime.LocalDateTime

@JsonClass(generateAdapter = true)
data class Record(
    val model: GeomagExt.Models,
    val time: LocalDateTime,
    val position: Position,
    val values: MagneticFieldExt
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Record -> {
                model == other.model
                && time == other.time
                && position == other.position
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = model.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + position.hashCode()
        result = 31 * result + values.hashCode()
        return result
    }

    companion object {
        fun empty() = Record(
            model = GeomagExt.Models.IGRF,
            time = LocalDateTime.now(),
            position = Position.empty(),
            values = MagneticFieldExt.empty()
        )
    }
}
