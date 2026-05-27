package com.mmfsin.streetparking.presentation.map

import androidx.lifecycle.viewModelScope
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.maps.model.LatLng
import com.mmfsin.streetparking.domain.models.Spot
import com.mmfsin.streetparking.domain.usecases.GetRadiusUseCase
import com.mmfsin.streetparking.domain.usecases.GetReclaimedSpotsUseCase
import com.mmfsin.streetparking.domain.usecases.GetSpotsUseCase
import com.mmfsin.streetparking.domain.usecases.ReclaimSpotUseCase
import com.mmfsin.streetparking.domain.usecases.UpdateRadiusUseCase
import com.mmfsin.streetparking.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getSpotsUseCase: GetSpotsUseCase,
    private val getRadiusUseCase: GetRadiusUseCase,
    private val getReclaimedSpotsUseCase: GetReclaimedSpotsUseCase,
    private val updateRadiusUseCase: UpdateRadiusUseCase,
    private val reclaimSpotUseCase: ReclaimSpotUseCase,
) : BaseViewModel<MapStates>(MapStates()) {

    private val radiusFlow = MutableStateFlow(0.0)
    private val selectedSpotFlow = MutableStateFlow<Spot?>(null)

    init {
        getRadius()
        observeReclaimedSpots()
    }

    fun getRadius() {
        executeUseCase(
            { getRadiusUseCase() },
            { result ->
                radiusFlow.value = result
                _uiState.update { it.copy(radius = result) }
            },
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

    fun getSpotsInRadius(userLocation: LatLng) {
        viewModelScope.launch {
            combine(
                getSpotsUseCase(userLocation),
                radiusFlow,
            ) { spots, radius ->
                filterSpotsByRadius(
                    spots = spots,
                    userLocation = userLocation,
                    radius = radius,
                )
            }.collect { filteredSpots ->
                _uiState.update {
                    it.copy(
                        spots = filteredSpots
                    )
                }
            }
        }
    }

    fun filterSpotsByRadius(
        spots: List<Spot>,
        userLocation: LatLng,
        radius: Double
    ): List<Spot> {

        val center = GeoLocation(userLocation.latitude, userLocation.longitude)

        return spots.filter { spot ->
            val distance = GeoFireUtils.getDistanceBetween(
                center,
                GeoLocation(spot.lat, spot.lng)
            )
            distance <= radius
        }
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
        selectedSpotFlow.value = spot
        _uiState.update { it.copy(selectedSpot = spot) }
    }

    fun reclaimSpot(id: String, spotLocation: LatLng, userLocation: LatLng) {
        executeUseCase(
            { reclaimSpotUseCase(id, spotLocation, userLocation) },
            {},
            {}
        )
    }

    private fun observeReclaimedSpots() {
        viewModelScope.launch {
            combine(
                getReclaimedSpotsUseCase(),
                selectedSpotFlow,
            ) { reclaimed, _ -> reclaimed }.collect { reclaimed ->
                _uiState.update { it.copy(reclaimedSpots = reclaimed) }
            }
        }
    }
}