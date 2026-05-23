package com.mmfsin.streetparking.presentation.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.streetparking.presentation.core.theme.BlueMedium

@Preview
@Composable
fun MapScreenPV() {
    MapContent(
        uiState = MapStates(

        )
    )
}

@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MapContent(
        uiState = uiState
    )
}

@Composable
fun MapContent(
    uiState: MapStates,
) {
    Box(Modifier.fillMaxSize().background(BlueMedium)) {

    }
}