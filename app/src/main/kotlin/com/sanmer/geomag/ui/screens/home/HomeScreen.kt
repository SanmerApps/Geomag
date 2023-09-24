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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.sanmer.geomag.model.Record
import com.sanmer.geomag.ui.component.Logo
import com.sanmer.geomag.ui.navigation.navigateToSettings
import com.sanmer.geomag.ui.providable.LocalUserPreferences
import com.sanmer.geomag.ui.screens.home.items.CalculationItem
import com.sanmer.geomag.ui.screens.home.items.DateTimeItem
import com.sanmer.geomag.ui.screens.home.items.LocationItem
import com.sanmer.geomag.ui.screens.home.items.RecordsItem
import com.sanmer.geomag.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val userPreferences = LocalUserPreferences.current

    var record by remember { mutableStateOf(Record.empty()) }
    val currentValue = viewModel.rememberCurrentValue()
    val dataTime = viewModel.rememberDateTime()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var showValue by rememberSaveable { mutableStateOf(false) }

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
                dateTime = dataTime,
                toggleDateTime = viewModel::toggleDateTime
            )

            LocationItem(
                isRunning = viewModel.isLocationRunning,
                position = viewModel.position,
                toggleLocation = viewModel::toggleLocation
            )

            CalculationItem(
                isRunning = viewModel.isCalculateRunning,
                fieldModel = userPreferences.fieldModel,
                navController = navController,
                setFieldModel = viewModel::setFieldModel,
                toggleCalculate = {
                    viewModel.toggleCalculate()
                    if (viewModel.isCalculateRunning) {
                        showValue = true
                    }
                },
                singleCalculate = {
                    scope.launch {
                        viewModel.singleCalculate().onSuccess {
                            record = it
                            showValue = true
                        }
                    }
                }
            )

            RecordsItem(
                enableRecords = userPreferences.enableRecords,
                navController = navController,
                setEnableRecords = viewModel::setEnableRecords,
                openBottomSheet = { showValue = true }
            )

            if (showValue) {
                RecordBottomSheet(
                    record = if (viewModel.isCalculateRunning) currentValue else record,
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
                containerColor = MaterialTheme.colorScheme.primary
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