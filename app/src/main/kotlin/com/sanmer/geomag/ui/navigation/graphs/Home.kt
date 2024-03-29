package com.sanmer.geomag.ui.navigation.graphs

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sanmer.geomag.ui.animate.slideInRightToLeft
import com.sanmer.geomag.ui.animate.slideOutLeftToRight
import com.sanmer.geomag.ui.navigation.MainScreen
import com.sanmer.geomag.ui.screens.customize.CustomizeScreen
import com.sanmer.geomag.ui.screens.home.HomeScreen

enum class HomeScreen(val route: String) {
    Home("Home"),
    Customize("Customize")
}

fun NavGraphBuilder.homeScreen(
    navController: NavController
) = navigation(
    startDestination = HomeScreen.Home.route,
    route = MainScreen.Home.route
) {
    composable(
        route = HomeScreen.Home.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        HomeScreen(
            navController = navController
        )
    }

    composable(
        route = HomeScreen.Customize.route,
        enterTransition = { slideInRightToLeft() + fadeIn() },
        exitTransition = { slideOutLeftToRight() + fadeOut() }
    ) {
        CustomizeScreen(
            navController = navController
        )
    }
}