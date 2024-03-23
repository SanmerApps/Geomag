package com.sanmer.geomag.ui.navigation

import androidx.navigation.NavController
import com.sanmer.geomag.ui.utils.navigatePopUpTo

enum class MainScreen(val route: String) {
    Home("HomeScreen"),
    Records("RecordsScreen"),
    Settings("SettingsScreen")
}

fun NavController.navigateToRecords() = navigatePopUpTo(MainScreen.Records.route)
fun NavController.navigateToSettings() = navigatePopUpTo(MainScreen.Settings.route)