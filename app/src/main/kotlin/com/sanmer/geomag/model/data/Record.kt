package com.sanmer.geomag.model.data

import com.sanmer.geomag.GeomagExt

data class Record(
    val model: GeomagExt.Models,
    val time: DateTime,
    val position: Position,
    val values: MagneticFieldExt
) {
    companion object {
        fun empty() = Record(
            model = GeomagExt.Models.IGRF,
            time = DateTime.now(),
            position = Position.empty(),
            values = MagneticFieldExt.empty()
        )
    }
}
