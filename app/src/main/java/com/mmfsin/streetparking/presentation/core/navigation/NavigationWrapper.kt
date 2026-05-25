package com.mmfsin.streetparking.presentation.core.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.presentation.core.components.StatusBarColor
import com.mmfsin.streetparking.presentation.core.components.Toolbar
import com.mmfsin.streetparking.presentation.home.HomeScreen
import com.mmfsin.streetparking.presentation.map.MapScreen
import com.mmfsin.streetparking.presentation.utils.BN_AUX_3
import com.mmfsin.streetparking.presentation.utils.BN_HOME_ID
import com.mmfsin.streetparking.presentation.utils.BN_MAP

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(id = BN_HOME_ID, name = stringResource(R.string.bottom_nav_home), icon = painterResource(R.drawable.ic_parking)),
        BottomNavItem(id = BN_MAP, name = stringResource(R.string.bottom_nav_aux2), icon = painterResource(R.drawable.ic_map_spot)),
        BottomNavItem(id = BN_AUX_3, name = stringResource(R.string.bottom_nav_aux3), icon = painterResource(R.drawable.ic_map_spot)),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    StatusBarColor()
    Scaffold(
        topBar = { Toolbar(text = R.string.app_name, iconBackVisible = false) },
        bottomBar = {
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination == item.id,
                        onClick = {
                            navController.navigate(item.id) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(painter = item.icon, contentDescription = item.name) },
                        label = { Text(text = item.name) },
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BN_HOME_ID,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BN_HOME_ID) { HomeScreen() }
            composable(route = BN_MAP) { MapScreen() }
            composable(route = BN_AUX_3) { HomeScreen() }
        }
    }
}

data class BottomNavItem(
    val id: String,
    val name: String,
    val icon: Painter,
)