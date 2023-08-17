package com.sanmer.geomag.ui.providable

import androidx.compose.runtime.compositionLocalOf
import com.sanmer.geomag.datastore.UserPreferencesExt

val LocalUserPreferences = compositionLocalOf { UserPreferencesExt.default() }
