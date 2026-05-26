package com.mmfsin.streetparking.presentation.leavespot

import com.google.android.gms.maps.model.LatLng
import com.mmfsin.streetparking.domain.usecases.CreateSpotUseCase
import com.mmfsin.streetparking.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LeaveSpotViewModel @Inject constructor(
    private val createSpotUseCase: CreateSpotUseCase,
) : BaseViewModel<LeaveSpotStates>(LeaveSpotStates()) {

    init {

    }

    fun createSpot(coordinates: LatLng) {
        executeUseCase(
            { createSpotUseCase(coordinates) },
            {
                _uiState.update { it.copy(spotCreated = true) }
                dialogLoadingVisibility(false)
            },
            {
                dialogLoadingVisibility(false)
            }
        )
    }

    fun dialogLoadingVisibility(visible: Boolean) {
        _uiState.update { it.copy(loadingDialog = visible) }
    }
}