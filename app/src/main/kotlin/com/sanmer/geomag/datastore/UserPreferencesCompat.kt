package com.sanmer.geomag.datastore

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.sanmer.geomag.Compat
import com.sanmer.geomag.compat.BuildCompat
import com.sanmer.geomag.ui.theme.Colors

data class UserPreferencesCompat(
    val darkMode: DarkMode,
    val themeColor: Int,
    val fieldModel: Compat.Models,
    val enableRecords: Boolean
) {
    constructor(original: UserPreferences) : this(
        darkMode = original.darkMode,
        themeColor = original.themeColor,
        fieldModel = Compat.Models.valueOf(original.fieldModel),
        enableRecords = original.enableRecords
    )

    @Composable
    fun isDarkMode() = when (darkMode) {
        DarkMode.ALWAYS_OFF -> false
        DarkMode.ALWAYS_ON -> true
        else -> isSystemInDarkTheme()
    }

    fun toProto(): UserPreferences = UserPreferences.newBuilder()
        .setDarkMode(darkMode)
        .setThemeColor(themeColor)
        .setFieldModel(fieldModel.name)
        .setEnableRecords(enableRecords)
        .build()

    companion object {
        fun default() = UserPreferencesCompat(
            darkMode = DarkMode.FOLLOW_SYSTEM,
            themeColor = if (BuildCompat.atLeastS) Colors.Dynamic.id else Colors.Pourville.id,
            fieldModel = Compat.Models.IGRF,
            enableRecords = true
        )

        fun UserPreferences.new(
            block: UserPreferencesKt.Dsl.() -> Unit
        ) = UserPreferencesCompat(this)
            .toProto()
            .copy(block)
    }
}