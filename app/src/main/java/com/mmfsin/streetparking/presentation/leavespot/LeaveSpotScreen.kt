package com.mmfsin.streetparking.presentation.leavespot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.presentation.core.components.MediumText
import com.mmfsin.streetparking.presentation.core.theme.GrayLight
import com.mmfsin.streetparking.presentation.core.theme.White

@Preview
@Composable
fun HomeScreenPV() {
    HomeContent(
        uiState = LeaveSpotStates()
    )
}

@Composable
fun HomeScreen(viewModel: LeaveSpotViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(
        uiState = uiState
    )
}

@Composable
fun HomeContent(
    uiState: LeaveSpotStates,
) {
    Box(Modifier.fillMaxSize().background(GrayLight)) {
        Button(onClick = {}, modifier = Modifier.align(Alignment.BottomCenter)) {
            MediumText(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                text = R.string.leave_spot_button,
                color = White,
                gravity = TextAlign.Center
            )
        }
    }
}