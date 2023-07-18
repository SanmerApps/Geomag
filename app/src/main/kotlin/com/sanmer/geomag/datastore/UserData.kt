package com.sanmer.geomag.datastore

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.sanmer.geomag.GeomagExt
import com.sanmer.geomag.app.utils.OsUtils
import com.sanmer.geomag.ui.theme.Colors

data class UserData(
    val darkMode: DarkMode,
    val themeColor: Int,
    val fieldModel: GeomagExt.Models,
    val enableRecords: Boolean
) {
    companion object {
        fun default() = UserData(
            darkMode = DarkMode.FOLLOW_SYSTEM,
            themeColor = if (OsUtils.atLeastS) Colors.Dynamic.id else Colors.Sakura.id,
            fieldModel = GeomagExt.Models.IGRF,
            enableRecords = true
        )
    }
}

@Composable
fun UserData.isDarkMode() = when (darkMode) {
    DarkMode.ALWAYS_OFF -> false
    DarkMode.ALWAYS_ON -> true
    else -> isSystemInDarkTheme()
}

fun UserData.toPreferences(): UserPreferences = UserPreferences.newBuilder()
    .setDarkMode(darkMode)
    .setThemeColor(themeColor)
    .setFieldModel(fieldModel.name)
    .setEnableRecords(enableRecords)
    .build()

fun UserPreferences.toUserData() = UserData(
    darkMode = darkMode,
    themeColor = themeColor,
    fieldModel = GeomagExt.Models.valueOf(fieldModel),
    enableRecords = enableRecords
)