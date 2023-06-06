package com.sanmer.geomag.ui.screens.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.sanmer.geomag.ui.component.SettingNormalItem
import com.sanmer.geomag.ui.component.SettingSwitchItem
import com.sanmer.geomag.ui.navigation.animated.SettingsScreen
import com.sanmer.geomag.ui.navigation.navigateToHome
import com.sanmer.geomag.ui.screens.settings.items.AppThemeItem
import com.sanmer.geomag.ui.utils.navigatePopUpTo
import com.sanmer.geomag.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val userData by viewModel.userData.collectAsStateWithLifecycle(UserData.default())

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    BackHandler { navController.navigateToHome() }

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

            SettingSwitchItem(
                iconRes = R.drawable.convertshape_outline,
                text = stringResource(id = R.string.settings_navigation_animation),
                subText = stringResource(id = R.string.settings_navigation_animation_desc),
                checked = userData.enableNavigationAnimation,
                onChange = viewModel::setEnableNavigationAnimation
            )
        }
    }
}

@Composable
private fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController
) = TopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.page_settings),
            style = MaterialTheme.typography.titleLarge
        )
    },
    navigationIcon = {
        IconButton(
            onClick = { navController.navigateToHome() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_left_outline),
                contentDescription = null
            )
        }
    },
    actions = {
        IconButton(
            onClick = { navController.navigatePopUpTo(SettingsScreen.About.route) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.star_outline),
                contentDescription = null
            )
        }
    },
    scrollBehavior = scrollBehavior
)