package com.sanmer.geomag.ui.activity.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sanmer.geomag.app.Config
import com.sanmer.geomag.app.Shortcut
import com.sanmer.geomag.ui.navigation.BottomNav
import com.sanmer.geomag.ui.navigation.MainGraph
import com.sanmer.geomag.ui.navigation.graph.HomeGraph
import com.sanmer.geomag.ui.navigation.graph.homeGraph
import com.sanmer.geomag.ui.navigation.graph.recordGraph
import com.sanmer.geomag.ui.navigation.graph.settingsGraph

@Composable
fun MainScreen() {
    val that = LocalContext.current as MainActivity

    val startDestination = when (that.intent.action) {
        Shortcut.ACTION_RECORDS -> MainGraph.Records.route
        Shortcut.ACTION_SETTINGS -> MainGraph.Settings.route
        else -> MainGraph.Home.route
    }

    if (Config.SIMPLE_MODE) {
        SimpleScreen(startDestination)
    } else {
        RegularScreen(startDestination)
    }
}

@Composable
private fun RegularScreen(
    startDestination: String
) {
    val navController = rememberAnimatedNavController()

    Scaffold(
        bottomBar = {
            BottomNav(navController = navController)
        },
        contentWindowInsets = WindowInsets.safeContent
    ) {
        AnimatedNavHost(
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            navController = navController,
            startDestination = startDestination,
            enterTransition = { fadeIn(animationSpec = tween(400)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            homeGraph(
                navController = navController,
                startDestination = HomeGraph.Regular.route
            )
            recordGraph(
                navController = navController
            )
            settingsGraph(
                navController = navController
            )
        }
    }
}

@Composable
private fun SimpleScreen(
    startDestination: String
) {
    val navController = rememberAnimatedNavController()

    Scaffold(
        contentWindowInsets = WindowInsets.statusBars
    ) {
        AnimatedNavHost(
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            navController = navController,
            startDestination = startDestination,
            enterTransition = { fadeIn(animationSpec = tween(400)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            homeGraph(
                navController = navController,
                startDestination = HomeGraph.Simple.route
            )
            recordGraph(
                navController = navController
            )
            settingsGraph(
                navController = navController
            )
        }
    }
}