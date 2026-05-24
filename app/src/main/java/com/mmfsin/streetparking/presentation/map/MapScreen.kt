package com.mmfsin.streetparking.presentation.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.mmfsin.streetparking.presentation.core.components.LoadingFullScreen
import com.mmfsin.streetparking.presentation.core.theme.BlueMedium
import com.mmfsin.streetparking.presentation.map.components.LocationErrorDialog
import com.mmfsin.streetparking.presentation.map.components.OnResume
import com.mmfsin.streetparking.presentation.map.components.RequestLocationPermissions
import com.mmfsin.streetparking.presentation.map.components.checkLocationPermissions

@Preview
@Composable
fun MapScreenPV() {
    MapContent(
        uiState = MapStates(
            hasLocationPermission = false,
        ),
        {}, {}
    )
}

@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MapContent(
        uiState = uiState,
        updatePermissionsState = { state -> viewModel.updatePermissionsState(state) },
        updateUserLocation = {}
    )
}

@Composable
fun MapContent(
    uiState: MapStates,
    updatePermissionsState: (Boolean) -> Unit,
    updateUserLocation: (Boolean) -> Unit,
) {

    val context = LocalContext.current

    OnResume {
        val granted = checkLocationPermissions(context)
        updatePermissionsState(granted)
    }

    val hasPermissionsGranted = checkLocationPermissions(context)

    if (hasPermissionsGranted) updatePermissionsState(true)
    else RequestLocationPermissions { isGranted ->
        if (isGranted) updatePermissionsState(true)
        else updatePermissionsState(false)
    }


    if (uiState.userLocation == null) LoadingFullScreen()
    uiState.userLocation?.let {
        Box(Modifier.fillMaxSize().background(BlueMedium)) {

            val madrid = LatLng(40.4168, -3.7038)

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(madrid, 12f)
                }
            )
        }
    }

    uiState.hasLocationPermission?.let { granted ->
        if (granted) permissionsGranted(context)
        else LocationErrorDialog()
    }
}

fun permissionsGranted(context: Context) {
    getUserLocation(
        context,
        userLocation = {},
        gpsOff = {}
    )
}

fun getUserLocation(
    context: Context,
    userLocation: () -> Unit,
    gpsOff: () -> Unit
) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isActive = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    if (isActive) getLocation(context, onLocation = { userLocation() })
    else gpsOff()
}

@SuppressLint("MissingPermission")
fun getLocation(
    context: Context,
    onLocation: (LatLng) -> Unit
) {
    val fused = LocationServices.getFusedLocationProviderClient(context)

    fused.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        CancellationTokenSource().token
    ).addOnSuccessListener { location ->
        if (location != null) {
            onLocation(LatLng(location.latitude, location.longitude))
        }
    }
}