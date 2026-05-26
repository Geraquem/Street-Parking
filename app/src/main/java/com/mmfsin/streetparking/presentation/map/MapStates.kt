package com.mmfsin.streetparking.presentation.map

import com.google.android.gms.maps.model.LatLng
import com.mmfsin.streetparking.domain.models.Spot

data class MapStates(
    val hasLocationPermission: Boolean = false,
    val userLocation: LatLng? = null,
    val isGPSActive: Boolean? = null,

    val showRadiusDialog: Boolean = false,

    val selectedSpot: Spot? = null,

    val radius: Double = 0.0,
    val spots: List<Spot> = emptyList()
)
