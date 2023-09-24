package com.sanmer.geomag.ui.providable

import androidx.compose.runtime.staticCompositionLocalOf
import com.sanmer.geomag.datastore.UserPreferencesExt

val LocalUserPreferences = staticCompositionLocalOf { UserPreferencesExt.default() }
