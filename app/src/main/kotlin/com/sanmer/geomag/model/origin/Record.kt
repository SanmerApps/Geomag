package com.sanmer.geomag.model.origin

import android.location.Location
import android.location.LocationManager
import com.sanmer.geomag.Compat
import dev.sanmer.geomag.MagneticField

data class Record(
    val model: Compat.Models,
    val time: DateTime,
    val location: Location,
    val values: MagneticField
) {
    companion object {
        fun empty() = Record(
            model = Compat.Models.IGRF,
            time = DateTime.now(),
            location = Location(LocationManager.GPS_PROVIDER),
            values = MagneticField(
                x = 0.0, xDot = 0.0,
                y = 0.0, yDot = 0.0,
                z = 0.0, zDot = 0.0,
                h = 0.0, hDot = 0.0,
                f = 0.0, fDot = 0.0,
                d = 0.0, dDot = 0.0,
                i = 0.0, iDot = 0.0
            )
        )
    }
}
