package com.sanmer.geomag.ui.navigation.navtype

import android.os.Bundle
import androidx.navigation.NavType
import com.sanmer.geomag.database.entity.RecordKey
import com.sanmer.geomag.utils.extensions.parcelable

val NavType.Companion.RecordKeyTyp get() = object : NavType<RecordKey>(isNullableAllowed = false) {
    override val name: String get() = "recordKey"

    override fun get(bundle: Bundle, key: String): RecordKey? {
        return bundle.parcelable(key)
    }

    override fun parseValue(value: String): RecordKey {
        return RecordKey.parse(value)
    }

    override fun put(bundle: Bundle, key: String, value: RecordKey) {
        bundle.putParcelable(key, value)
    }
}