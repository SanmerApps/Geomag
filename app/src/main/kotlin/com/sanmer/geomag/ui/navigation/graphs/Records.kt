package com.sanmer.geomag.ui.navigation.graphs

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.sanmer.geomag.ui.animate.slideInLeftToRight
import com.sanmer.geomag.ui.animate.slideInRightToLeft
import com.sanmer.geomag.ui.animate.slideOutLeftToRight
import com.sanmer.geomag.ui.animate.slideOutRightToLeft
import com.sanmer.geomag.ui.navigation.MainScreen
import com.sanmer.geomag.ui.screens.records.RecordsScreen
import com.sanmer.geomag.ui.screens.records.view.ViewScreen

enum class RecordsScreen(val route: String) {
    Home("Records"),
    View("View/{recordKey}")
}

private val subScreens = listOf(
    RecordsScreen.View.route
)

fun NavGraphBuilder.recordsScreen(
    navController: NavController
) = navigation(
    startDestination = RecordsScreen.Home.route,
    route = MainScreen.Records.route
) {
    composable(
        route = RecordsScreen.Home.route,
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
        RecordsScreen(
            navController = navController
        )
    }

    composable(
        route = RecordsScreen.View.route,
        arguments = listOf(navArgument("recordKey") { type = NavType.StringType }),
        enterTransition = { slideInRightToLeft() + fadeIn() },
        exitTransition = { slideOutLeftToRight() + fadeOut() }
    ) {
        ViewScreen(
            navController = navController
        )
    }
}