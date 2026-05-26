package com.mmfsin.streetparking.presentation.map

import com.google.android.gms.maps.model.LatLng
import com.mmfsin.streetparking.domain.models.Spot
import com.mmfsin.streetparking.domain.usecases.GetRadiusUseCase
import com.mmfsin.streetparking.domain.usecases.GetSpotsUseCase
import com.mmfsin.streetparking.domain.usecases.UpdateRadiusUseCase
import com.mmfsin.streetparking.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getSpotsUseCase: GetSpotsUseCase,
    private val getRadiusUseCase: GetRadiusUseCase,
    private val updateRadiusUseCase: UpdateRadiusUseCase,
) : BaseViewModel<MapStates>(MapStates()) {

    init {
        getRadius()
    }

    fun getRadius() {
        executeUseCase(
            { getRadiusUseCase() },
            { result -> _uiState.update { it.copy(radius = result) } },
            {}
        )
    }

    fun updatePermissionsState(granted: Boolean) {
        _uiState.update { it.copy(hasLocationPermission = granted) }
    }

    fun updateUserLocation(location: LatLng) {
        _uiState.update { it.copy(userLocation = location) }
    }

    fun updateGPSActive(active: Boolean) {
        if (active) {
            _uiState.update { it.copy(isGPSActive = true) }
        } else {
            _uiState.update {
                it.copy(
                    userLocation = null,
                    isGPSActive = false
                )
            }
        }
    }

    fun getSpots() {
        executeUseCase(
            { getSpotsUseCase() },
            { result -> _uiState.update { it.copy(spots = result) } },
            {}
        )
    }

    fun updateRadius(newRadius: Double) {
        showRadiusDialog(false)
        executeUseCase(
            { updateRadiusUseCase(newRadius) },
            { getRadius() },
            {}
        )
    }

    fun showRadiusDialog(visible: Boolean) {
        _uiState.update { it.copy(showRadiusDialog = visible) }
    }

    fun updateSelectedSpot(spot: Spot?) {
        _uiState.update { it.copy(selectedSpot = spot) }
    }
}