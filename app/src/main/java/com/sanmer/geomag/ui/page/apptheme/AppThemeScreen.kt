package com.sanmer.geomag.ui.page.apptheme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.component.NormalTitle
import com.sanmer.geomag.ui.expansion.navigateBack
import com.sanmer.geomag.ui.page.apptheme.DarkModeItem
import com.sanmer.geomag.ui.page.apptheme.ExampleItem
import com.sanmer.geomag.ui.page.apptheme.ThemePaletteItem
import com.sanmer.geomag.ui.utils.NavigateUpTopBar

@Composable
fun AppThemeScreen(
    navController: NavController
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    BackHandler { navController.navigateBack() }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppThemeTopBar(
                scrollBehavior = scrollBehavior,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
        ) {
            NormalTitle(text = stringResource(id = R.string.settings_example))
            ExampleItem()

            NormalTitle(text = stringResource(id = R.string.settings_theme_palette))
            ThemePaletteItem()

            NormalTitle(text = stringResource(id = R.string.settings_dark_theme))
            DarkModeItem()
        }
    }
}

@Composable
private fun AppThemeTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController
) = NavigateUpTopBar(
    title = R.string.settings_app_theme,
    scrollBehavior = scrollBehavior,
    navController = navController
)