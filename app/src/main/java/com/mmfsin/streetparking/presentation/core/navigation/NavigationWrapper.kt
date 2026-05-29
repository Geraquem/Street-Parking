package com.mmfsin.streetparking.presentation.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.presentation.core.components.LoadingFullScreen
import com.mmfsin.streetparking.presentation.core.components.Toolbar
import com.mmfsin.streetparking.presentation.leavespot.HomeScreen
import com.mmfsin.streetparking.presentation.main.MainViewModel
import com.mmfsin.streetparking.presentation.map.MapScreen
import com.mmfsin.streetparking.presentation.utils.LEAVE_SPOT_SCREEN
import com.mmfsin.streetparking.presentation.utils.LOADING_SCREEN
import com.mmfsin.streetparking.presentation.utils.MAPS_SCREEN
import com.mmfsin.streetparking.presentation.utils.WHERE_PARKED_SCREEN
import kotlinx.coroutines.launch

@Composable
fun NavigationWrapper(viewModel: MainViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    //
    //
    //    val tabs = listOf(
    //        TabData(stringResource(R.string.bottom_nav_leave_spot), R.drawable.ic_parking),
    //        TabData(stringResource(R.string.bottom_nav_search_spot), R.drawable.ic_map_spot),
    //
    //        )
    //    val selectedTab = remember { mutableIntStateOf(0) }
    //
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    LaunchedEffect(uiState.isDrawerOpened) {
        if (uiState.isDrawerOpened) drawerState.open()
        else drawerState.close()
    }

    LaunchedEffect(drawerState) {
        snapshotFlow { drawerState.isOpen }.collect { isOpen ->
            viewModel.navDrawerOpened(isOpen)
        }
    }

    LaunchedEffect(uiState.lastScreen) {
        if (uiState.lastScreen != LOADING_SCREEN) {
            navController.navigate(uiState.lastScreen) {
                popUpTo(LOADING_SCREEN) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                text = R.string.app_name,
                iconBackVisible = false,
                onRightIconClick = { viewModel.navDrawerOpened(opened = !uiState.isDrawerOpened) }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {

            ModalNavigationDrawer(
                gesturesEnabled = drawerState.isOpen,
                drawerState = drawerState,
                drawerContent = {
                    DrawerContent(
                        onDestinationClick = { route ->
                            scope.launch { viewModel.navDrawerOpened(opened = false) }
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            ) {
                NavHost(navController, startDestination = LOADING_SCREEN) {
                    composable(LOADING_SCREEN) { LoadingFullScreen() }

                    composable(LEAVE_SPOT_SCREEN) {
                        viewModel.updateLastScreen(LEAVE_SPOT_SCREEN)
                        HomeScreen()
                    }
                    composable(MAPS_SCREEN) {
                        viewModel.updateLastScreen(MAPS_SCREEN)
                        MapScreen()
                    }
                    composable(WHERE_PARKED_SCREEN) {
                        viewModel.updateLastScreen(WHERE_PARKED_SCREEN)
                        HomeScreen()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DrawerContentPV() {
    DrawerContent { }
}

@Composable
fun DrawerContent(onDestinationClick: (String) -> Unit) {
    ModalDrawerSheet {
        //        Text("Mi App", modifier = Modifier.padding(16.dp))

        NavigationDrawerItem(
            label = { Text("Screen A") },
            selected = false,
            onClick = { onDestinationClick(LEAVE_SPOT_SCREEN) }
        )

        NavigationDrawerItem(
            label = { Text("Screen B") },
            selected = false,
            onClick = { onDestinationClick(MAPS_SCREEN) }
        )

        NavigationDrawerItem(
            label = { Text("Screen C") },
            selected = false,
            onClick = { onDestinationClick(WHERE_PARKED_SCREEN) }
        )
    }
}