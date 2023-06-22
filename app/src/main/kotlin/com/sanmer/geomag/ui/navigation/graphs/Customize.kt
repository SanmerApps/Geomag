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

enum class CustomizeScreen(val route: String) {
    Home("Customize")
}

fun NavGraphBuilder.customizeScreen(
    navController: NavController
) = navigation(
    startDestination = CustomizeScreen.Home.route,
    route = MainScreen.Calculate.route
) {
    composable(
        route = CustomizeScreen.Home.route,
        enterTransition = { slideInRightToLeft() + fadeIn() },
        exitTransition = { slideOutLeftToRight() + fadeOut() }
    ) {
        CustomizeScreen(
            navController = navController
        )
    }
}