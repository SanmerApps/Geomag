package com.sanmer.geomag.ui.screens.customize

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.component.NavigateUpTopBar
import com.sanmer.geomag.ui.component.PageIndicator

@Composable
fun CustomizeScreen(
    navController: NavController
) {
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
        PageIndicator(
            icon = R.drawable.edit_bold,
            text = "CustomizeScreen",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController
) = NavigateUpTopBar(
    title = stringResource(id = R.string.page_customize),
    scrollBehavior = scrollBehavior,
    navController = navController
)