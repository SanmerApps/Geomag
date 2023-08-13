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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.datastore.UserData
import com.sanmer.geomag.datastore.isDarkMode
import com.sanmer.geomag.ui.activity.log.LogActivity
import com.sanmer.geomag.ui.component.NavigateUpTopBar
import com.sanmer.geomag.ui.component.SettingNormalItem
import com.sanmer.geomag.ui.navigation.graphs.SettingsScreen
import com.sanmer.geomag.ui.screens.settings.items.AppThemeItem
import com.sanmer.geomag.ui.utils.navigateSingleTopTo
import com.sanmer.geomag.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val userData by viewModel.userData.collectAsStateWithLifecycle(UserData.default())

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
            SettingNormalItem(
                iconRes = R.drawable.health_outline,
                text = stringResource(id = R.string.settings_log_viewer),
                subText = stringResource(id = R.string.settings_log_viewer_desc),
                onClick = {
                    LogActivity.start(context)
                }
            )

            AppThemeItem(
                themeColor = userData.themeColor,
                darkMode = userData.darkMode,
                isDarkMode = userData.isDarkMode(),
                onThemeColorChange = viewModel::setThemeColor,
                onDarkModeChange =viewModel::setDarkTheme
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
                painter = painterResource(id = R.drawable.star_outline),
                contentDescription = null
            )
        }
    }
)