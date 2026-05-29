package com.mmfsin.streetparking.presentation.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.domain.models.DrawerData
import com.mmfsin.streetparking.domain.models.DrawerDirection.*
import com.mmfsin.streetparking.domain.models.DrawerDirection.Companion.getDrawerItems
import com.mmfsin.streetparking.presentation.core.components.LoadingFullScreen
import com.mmfsin.streetparking.presentation.core.components.MediumText
import com.mmfsin.streetparking.presentation.core.components.Toolbar
import com.mmfsin.streetparking.presentation.core.theme.GrayMedium
import com.mmfsin.streetparking.presentation.leavespot.HomeScreen
import com.mmfsin.streetparking.presentation.main.MainViewModel
import com.mmfsin.streetparking.presentation.map.MapScreen
import com.mmfsin.streetparking.presentation.utils.LOADING_SCREEN
import com.mmfsin.streetparking.presentation.whereparked.WhereParkedScreen
import kotlinx.coroutines.launch

@Composable
fun NavigationWrapper(viewModel: MainViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                        getDrawerItems(),
                        selectedIndex = uiState.lastScreenIndex,
                        onDestinationClick = { i, destination ->
                            scope.launch {
                                viewModel.updateLastScreenIndex(i)
                                viewModel.navDrawerOpened(opened = false)
                            }
                            navController.navigate(destination) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            ) {
                NavHost(navController, startDestination = LOADING_SCREEN) {
                    composable(LOADING_SCREEN) { LoadingFullScreen() }

                    composable(LEAVING_SPOT.name) {
                        viewModel.updateLastScreen(LEAVING_SPOT.name)
                        HomeScreen()
                    }
                    composable(SEARCHING_PLACE.name) {
                        viewModel.updateLastScreen(SEARCHING_PLACE.name)
                        MapScreen()
                    }
                    composable(WHERE_PARKED.name) {
                        viewModel.updateLastScreen(WHERE_PARKED.name)
                        WhereParkedScreen()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DrawerContentPV() {
    DrawerContent(getDrawerItems(), 0) { _, _ -> }
}

@Composable
fun DrawerContent(items: List<DrawerData>, selectedIndex: Int, onDestinationClick: (Int, String) -> Unit) {
    ModalDrawerSheet(
        drawerShape = RoundedCornerShape(topEnd = 50.dp),
    ) {
        DrawerHeader()
        Spacer(Modifier.height(64.dp))
        items.forEachIndexed { i, item ->
            DrawerItem(
                item = item,
                selected = i == selectedIndex,
                onClick = {
                    onDestinationClick(i, item.direction)
                }
            )
        }
    }
}

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier.fillMaxWidth().height(150.dp).background(GrayMedium),
        contentAlignment = Alignment.Center
    ) {
        Icon(painter = painterResource(R.drawable.ic_bullseye), null)
    }
}

@Composable
fun DrawerItem(
    item: DrawerData,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        selected = selected,
        label = { MediumText(text = item.text) },
        icon = { Icon(painterResource(item.icon), null) },
        onClick = { onClick() }
    )
}