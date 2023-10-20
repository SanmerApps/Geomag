package com.sanmer.geomag.ui.screens.records

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.component.Loading
import com.sanmer.geomag.ui.component.PageIndicator
import com.sanmer.geomag.viewmodel.RecordsViewModel

@Composable
fun RecordsScreen(
    navController: NavController,
    viewModel: RecordsViewModel = hiltViewModel()
) {
    val list by viewModel.records.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listSate = rememberLazyListState()
    
    LaunchedEffect(viewModel.selectedSize) {
        if (viewModel.selectedSize == 0) {
            viewModel.closeChooser()
        }
    }

    BackHandler(
        enabled = viewModel.isChooser,
        onBack = viewModel::closeChooser
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                isChooser = viewModel.isChooser,
                selectedSize = viewModel.selectedSize,
                onCloseChooser = viewModel::closeChooser,
                shareRecords = viewModel::shareJsonFile,
                deleteRecords = viewModel::deleteSelected,
                scrollBehavior = scrollBehavior,
                navController = navController
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (viewModel.isLoading) {
                Loading()
            }

            if (list.isEmpty() && !viewModel.isLoading) {
                PageIndicator(
                    icon = R.drawable.database_search,
                    text = R.string.records_empty
                )
            }

            RecordsList(
                list = list,
                state = listSate,
                isSelected = viewModel::isSelected,
                isChooser = viewModel.isChooser,
                onOpenChooser = { viewModel.isChooser = true },
                onToggle = viewModel::toggleRecord,
                navController = navController
            )
        }
    }
}

@Composable
private fun TopBar(
    isChooser: Boolean,
    selectedSize: Int,
    onCloseChooser: () -> Unit,
    shareRecords: (Context) -> Unit,
    deleteRecords: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController
) = TopAppBar(
    title = {
        Text(
            text = if (isChooser) {
                selectedSize.toString()
            } else {
                stringResource(id = R.string.page_records)
            },
            style = MaterialTheme.typography.titleLarge
        )
    },
    navigationIcon = {
        IconButton(
            onClick = {
                if (isChooser) {
                    onCloseChooser()
                } else {
                    navController.popBackStack()
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = null
            )
        }
    },
    actions = {
        val context = LocalContext.current
         if (isChooser) {
             IconButton(
                 onClick = { shareRecords(context) }
             ) {
                 Icon(
                     painter = painterResource(id = R.drawable.share_2),
                     contentDescription = null
                 )
             }

             var delete by remember { mutableStateOf(false) }
             if (delete) DeleteDialog(
                 onClose = { delete = false },
                 deleteRecords = deleteRecords
             )

             IconButton(
                 onClick = { delete = true }
             ) {
                 Icon(
                     painter = painterResource(id = R.drawable.trash),
                     contentDescription = null
                 )
             }
         }
    },
    scrollBehavior = scrollBehavior
)

@Composable
private fun DeleteDialog(
    onClose: () -> Unit,
    deleteRecords: () -> Unit
) = AlertDialog(
    onDismissRequest = onClose,
    shape = RoundedCornerShape(20.dp),
    title = { Text(text = stringResource(id = R.string.records_dialog_delete_title)) },
    text = { Text(text = stringResource(id = R.string.records_dialog_delete_desc)) },
    confirmButton = {
        TextButton(
            onClick = {
                deleteRecords()
                onClose()
            }
        ) {
            Text(text = stringResource(id = R.string.dialog_delete))
        }
    },
    dismissButton = {
        TextButton(
            onClick = onClose
        ) {
            Text(text = stringResource(id = R.string.dialog_cancel))
        }
    },
)