package com.sanmer.geomag.ui.navigation.animated

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.sanmer.geomag.database.entity.primaryKey
import com.sanmer.geomag.model.Record
import com.sanmer.geomag.ui.animate.slideInLeftToRight
import com.sanmer.geomag.ui.animate.slideInRightToLeft
import com.sanmer.geomag.ui.animate.slideOutLeftToRight
import com.sanmer.geomag.ui.animate.slideOutRightToLeft
import com.sanmer.geomag.ui.navigation.MainScreen
import com.sanmer.geomag.ui.screens.records.RecordsScreen
import com.sanmer.geomag.ui.screens.records.viewrecord.ViewRecordScreen

enum class RecordsScreen(val route: String) {
    Home("Records"),
    View("View/{primaryKey}")
}

fun createViewRoute(record: Record) = "View/${record.primaryKey}"

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
        arguments = listOf(navArgument("primaryKey") { type = NavType.StringType }),
        enterTransition = { slideInRightToLeft() + fadeIn() },
        exitTransition = { slideOutLeftToRight() + fadeOut() }
    ) {
        ViewRecordScreen(
            navController = navController
        )
    }
}