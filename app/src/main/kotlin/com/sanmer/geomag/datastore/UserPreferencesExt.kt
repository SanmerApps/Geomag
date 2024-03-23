package com.sanmer.geomag.datastore

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.sanmer.geomag.GeomagExt
import com.sanmer.geomag.compat.BuildCompat
import com.sanmer.geomag.ui.theme.Colors

data class UserPreferencesExt(
    val darkMode: DarkMode,
    val themeColor: Int,
    val fieldModel: GeomagExt.Models,
    val enableRecords: Boolean
) {
    companion object {
        fun default() = UserPreferencesExt(
            darkMode = DarkMode.FOLLOW_SYSTEM,
            themeColor = if (BuildCompat.atLeastS) Colors.Dynamic.id else Colors.Pourville.id,
            fieldModel = GeomagExt.Models.IGRF,
            enableRecords = true
        )
    }
}

@Composable
fun UserPreferencesExt.isDarkMode() = when (darkMode) {
    DarkMode.ALWAYS_OFF -> false
    DarkMode.ALWAYS_ON -> true
    else -> isSystemInDarkTheme()
}

fun UserPreferencesExt.toProto(): UserPreferences = UserPreferences.newBuilder()
    .setDarkMode(darkMode)
    .setThemeColor(themeColor)
    .setFieldModel(fieldModel.name)
    .setEnableRecords(enableRecords)
    .build()

fun UserPreferences.toExt() = UserPreferencesExt(
    darkMode = darkMode,
    themeColor = themeColor,
    fieldModel = GeomagExt.Models.valueOf(fieldModel),
    enableRecords = enableRecords
)

fun UserPreferences.new(
    block: UserPreferencesKt.Dsl.() -> Unit
) = toExt()
    .toProto()
    .copy(block)