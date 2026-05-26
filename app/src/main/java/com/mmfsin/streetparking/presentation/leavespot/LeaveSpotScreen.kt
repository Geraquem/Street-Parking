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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.LatLng
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.presentation.core.components.DialogLoading
import com.mmfsin.streetparking.presentation.core.components.MediumText
import com.mmfsin.streetparking.presentation.core.theme.GrayLight
import com.mmfsin.streetparking.presentation.core.theme.White
import com.mmfsin.streetparking.presentation.map.helper.getUserLocation

@Preview
@Composable
fun HomeScreenPV() {
    HomeContent(
        uiState = LeaveSpotStates(
            loadingDialog = true
        ),
        {}, {},
    )
}

@Composable
fun HomeScreen(viewModel: LeaveSpotViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(
        uiState = uiState,
        dialogLoadingVisibility = { visible -> viewModel.dialogLoadingVisibility(visible) },
        createSpot = { coordinates -> viewModel.createSpot(coordinates) }
    )
}

@Composable
fun HomeContent(
    uiState: LeaveSpotStates,
    dialogLoadingVisibility: (Boolean) -> Unit,
    createSpot: (LatLng) -> Unit,
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
            .background(GrayLight)
            .padding(horizontal = 16.dp)
            .padding(bottom = 24.dp)
    ) {
        Button(
            onClick = {
                dialogLoadingVisibility(true)
                getUserLocation(
                    context = context,
                    userLocation = { coordinates -> createSpot(coordinates) },
                )
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            MediumText(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                text = R.string.leave_spot_button,
                color = White,
                gravity = TextAlign.Center
            )
        }

        if (uiState.loadingDialog) DialogLoading()
    }
}
