@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.streetparking.presentation.core.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.presentation.core.theme.White

@Preview
@Composable
fun ToolbarPV() {
    Toolbar(
        text = R.string.app_name,
        onBackClick = {},
        iconBackVisible = true,
        onRightIconClick = {}
    )
}

@Composable
fun Toolbar(
    text: Int = R.string.empty,
    iconBackVisible: Boolean = true,
    onBackClick: () -> Unit = {},
    onRightIconClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier
            .zIndex(1f)
            .shadow(
                elevation = 4.dp,
                clip = false
            ),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(text),
                    modifier = Modifier.weight(1f).padding(start = if (iconBackVisible) 2.dp else 16.dp),
                )

                IconButton(onClick = onRightIconClick, modifier = Modifier.padding(start = 0.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.ic_drawer_menu),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },

        navigationIcon = {
            if (iconBackVisible) {
                IconButton(onClick = onBackClick, modifier = Modifier.padding(start = 0.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = stringResource(R.string.cd_arrow_back),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    )
}