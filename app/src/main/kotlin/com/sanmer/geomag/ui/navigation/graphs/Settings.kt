package com.sanmer.geomag.ui.navigation.graphs

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sanmer.geomag.ui.animate.slideInLeftToRight
import com.sanmer.geomag.ui.animate.slideInRightToLeft
import com.sanmer.geomag.ui.animate.slideOutLeftToRight
import com.sanmer.geomag.ui.animate.slideOutRightToLeft
import com.sanmer.geomag.ui.navigation.MainScreen
import com.sanmer.geomag.ui.screens.settings.SettingsScreen
import com.sanmer.geomag.ui.screens.settings.about.AboutScreen
import com.sanmer.geomag.ui.screens.settings.gnss.GnssStatusScreen

enum class SettingsScreen(val route: String) {
    Home("Settings"),
    About("About"),
    Gnss("Gnss")
}

private val subScreens = listOf(
    SettingsScreen.About.route,
    SettingsScreen.Gnss.route
)

fun NavGraphBuilder.settingsScreen(
    navController: NavController
) = navigation(
    startDestination = SettingsScreen.Home.route,
    route = MainScreen.Settings.route
) {
    composable(
        route = SettingsScreen.Home.route,
        enterTransition = {
            if (initialState.destination.route in subScreens) {
                slideInLeftToRight()
            } else {
                slideInRightToLeft()
            } + fadeIn()
        },
        exitTransition = {
            if (targetState.destination.route in subScreens) {
                slideOutRightToLeft()
            } else {
                slideOutLeftToRight()
            } + fadeOut()
        }
    ) {
        SettingsScreen(
            navController = navController
        )
    }

    composable(
        route = SettingsScreen.About.route,
        enterTransition = { slideInRightToLeft() + fadeIn() },
        exitTransition = { slideOutLeftToRight() + fadeOut() }
    ) {
        AboutScreen(
            navController = navController
        )
    }

    composable(
        route = SettingsScreen.Gnss.route,
        enterTransition = { slideInRightToLeft() + fadeIn() },
        exitTransition = { slideOutLeftToRight() + fadeOut() }
    ) {
        GnssStatusScreen(
            navController = navController
        )
    }
}