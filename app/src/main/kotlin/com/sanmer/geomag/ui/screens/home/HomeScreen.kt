package com.sanmer.geomag.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.component.Logo
import com.sanmer.geomag.ui.navigation.navigateToSettings
import com.sanmer.geomag.ui.providable.LocalUserPreferences
import com.sanmer.geomag.ui.screens.home.items.CalculationItem
import com.sanmer.geomag.ui.screens.home.items.DateTimeItem
import com.sanmer.geomag.ui.screens.home.items.LocationItem
import com.sanmer.geomag.ui.screens.home.items.RecordsItem
import com.sanmer.geomag.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val userPreferences = LocalUserPreferences.current

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var showValue by rememberSaveable { mutableStateOf(false) }

    viewModel.DateTimeObserver()

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
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DateTimeItem(
                isRunning = viewModel.isTimeRunning,
                dateTime = viewModel.dateTime,
                toggleDateTime = viewModel::toggleDateTime
            )

            LocationItem(
                isRunning = viewModel.isLocationRunning,
                position = viewModel.position,
                toggleLocation = viewModel::toggleLocation
            )

            CalculationItem(
                navController = navController,
                fieldModel = userPreferences.fieldModel,
                setFieldModel = viewModel::setFieldModel,
                singleCalculate = {
                    viewModel.singleCalculate()
                    showValue = true
                }
            )

            RecordsItem(
                navController = navController,
                enableRecords = userPreferences.enableRecords,
                setEnableRecords = viewModel::setEnableRecords
            )

            if (showValue) {
                RecordBottomSheet(
                    record = viewModel.record,
                    onClose = { showValue = false }
                )
            }
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
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge
        )
    },
    navigationIcon = {
        Box(
            modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            Logo(
                icon = R.drawable.launcher_outline,
                modifier = Modifier.size(32.dp),
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,
                fraction = 0.65f
            )
        }
    },
    actions = {
        IconButton(
            onClick = { navController.navigateToSettings() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = null
            )
        }
    },
    scrollBehavior = scrollBehavior
)