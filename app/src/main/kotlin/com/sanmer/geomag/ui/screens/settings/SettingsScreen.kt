package com.sanmer.geomag.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.component.NavigateUpTopBar
import com.sanmer.geomag.ui.component.SettingNormalItem
import com.sanmer.geomag.ui.navigation.graphs.SettingsScreen
import com.sanmer.geomag.ui.providable.LocalUserPreferences
import com.sanmer.geomag.ui.screens.settings.items.AppThemeItem
import com.sanmer.geomag.ui.utils.navigateSingleTopTo
import com.sanmer.geomag.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val userPreferences = LocalUserPreferences.current

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AppThemeItem(
                themeColor = userPreferences.themeColor,
                darkMode = userPreferences.darkMode,
                isDarkMode = userPreferences.isDarkMode(),
                onThemeColorChange = viewModel::setThemeColor,
                onDarkModeChange = viewModel::setDarkTheme
            )

            SettingNormalItem(
                icon = R.drawable.satellite,
                title = stringResource(id = R.string.settings_satellite_status),
                desc = stringResource(id = R.string.settings_satellite_status_desc),
                onClick = { navController.navigateSingleTopTo(SettingsScreen.Gnss.route) }
            )
        }
    }
}

@Composable
private fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController
) = NavigateUpTopBar(
    title = stringResource(id = R.string.page_settings),
    scrollBehavior = scrollBehavior,
    navController = navController,
    actions = {
        IconButton(
            onClick = { navController.navigateSingleTopTo(SettingsScreen.About.route) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.candy),
                contentDescription = null
            )
        }
    }
)