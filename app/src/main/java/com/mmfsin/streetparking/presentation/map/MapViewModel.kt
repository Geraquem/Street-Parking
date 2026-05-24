package com.mmfsin.streetparking.presentation.map

import com.mmfsin.streetparking.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(

) : BaseViewModel<MapStates>(MapStates()) {

    init {
        checkForLocationPermission()
    }

    fun checkForLocationPermission() {

    }

    fun updatePermissionsState(granted: Boolean) {
        _uiState.update { it.copy(hasLocationPermission = granted) }
    }
}