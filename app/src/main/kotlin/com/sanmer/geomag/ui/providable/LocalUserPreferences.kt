package com.sanmer.geomag.ui.providable

import androidx.compose.runtime.staticCompositionLocalOf
import com.sanmer.geomag.datastore.UserPreferencesCompat

val LocalUserPreferences = staticCompositionLocalOf { UserPreferencesCompat.default() }
