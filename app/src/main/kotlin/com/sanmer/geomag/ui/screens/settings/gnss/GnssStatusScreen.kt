package com.sanmer.geomag.ui.screens.settings.gnss

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.service.LocationService
import com.sanmer.geomag.ui.component.Loading
import com.sanmer.geomag.ui.component.NavigateUpTopBar

@Composable
fun GnssStatusScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listSate = rememberLazyListState()

    val isRunning by remember {
        derivedStateOf { LocationService.isRunning }
    }

    val list by remember(LocationService.gnssStatusOrNull) {
        derivedStateOf { LocationService.getSatelliteList() }
    }

    DisposableEffect(true) {
        if (!isRunning) {
            LocationService.start(context)
        }

        onDispose {
            if (isRunning) {
                LocationService.stop(context)
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                navController = navController
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (list.isEmpty()) {
                Loading()
            }

            SatelliteList(
                list = list,
                state = listSate
            )
        }
    }
}

@Composable
private fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController
) = NavigateUpTopBar(
    title = stringResource(id = R.string.page_gnss),
    scrollBehavior = scrollBehavior,
    navController = navController,
)