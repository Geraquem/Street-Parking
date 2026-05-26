package com.mmfsin.streetparking.presentation.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.presentation.core.components.Toolbar
import com.mmfsin.streetparking.presentation.core.theme.Black
import com.mmfsin.streetparking.presentation.core.theme.GrayHard
import com.mmfsin.streetparking.presentation.leavespot.HomeScreen
import com.mmfsin.streetparking.presentation.map.MapScreen

@Preview
@Composable
fun NavigationWrapper() {
    val tabs = listOf(
        TabData(stringResource(R.string.bottom_nav_leave_spot), R.drawable.ic_parking),
        TabData(stringResource(R.string.bottom_nav_search_spot), R.drawable.ic_map_spot),

        )
    val selectedTab = remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = { Toolbar(text = R.string.app_name, iconBackVisible = false) },
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {

            when (selectedTab.intValue) {
                0 -> HomeScreen()
                1 -> MapScreen()
            }

            Surface(
                modifier = Modifier.padding(12.dp),
                shape = RoundedCornerShape(50),
                shadowElevation = 6.dp,
                color = Color.White
            ) {
                PrimaryTabRow(
                    selectedTabIndex = selectedTab.intValue,
                    divider = {},
                    indicator = {
                        Box(
                            Modifier
                                .tabIndicatorOffset(selectedTab.intValue)
                                .fillMaxHeight()
                                .padding(8.dp)
                                .clip(RoundedCornerShape(50))
                                .background(GrayHard.copy(alpha = 0.2f))
                        )
                    }
                ) {

                    tabs.forEachIndexed { index, tab ->
                        Tab(
                            selected = selectedTab.intValue == index,
                            onClick = { selectedTab.intValue = index },
                            interactionSource = remember { MutableInteractionSource() },
                        ) {

                            Box(
                                modifier = Modifier.padding(vertical = 16.dp)
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) {
                                        selectedTab.intValue = index
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = tab.title,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

data class TabData(
    val title: String,
    val icon: Int
)