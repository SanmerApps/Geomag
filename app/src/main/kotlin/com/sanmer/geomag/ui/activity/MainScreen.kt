package com.sanmer.geomag.ui.activity

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sanmer.geomag.ui.navigation.MainScreen
import com.sanmer.geomag.ui.navigation.graphs.customizeScreen
import com.sanmer.geomag.ui.navigation.graphs.homeScreen
import com.sanmer.geomag.ui.navigation.graphs.recordsScreen
import com.sanmer.geomag.ui.navigation.graphs.settingsScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = MainScreen.Home.route
        ) {
            homeScreen(
                navController = navController
            )
            customizeScreen(
                navController = navController
            )
            recordsScreen(
                navController = navController
            )
            settingsScreen(
                navController = navController
            )
        }
    }
}