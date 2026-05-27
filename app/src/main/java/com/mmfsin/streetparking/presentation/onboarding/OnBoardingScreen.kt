package com.mmfsin.streetparking.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.streetparking.presentation.core.theme.RedHard

@Preview
@Composable
fun OnBoardingPV() {
    OnBoardingContent(
        uiState = OnBoardingStates(

        ),
    )
}

@Composable
fun OnBoardingScreen(viewModel: OnBoardingViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    OnBoardingContent(
        uiState = uiState,
    )
}

@Composable
fun OnBoardingContent(
    uiState: OnBoardingStates,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(RedHard)
    ) {

    }
}