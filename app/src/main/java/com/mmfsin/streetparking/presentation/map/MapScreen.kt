package com.mmfsin.streetparking.presentation.map

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.domain.models.RadiusTypes.Companion.getTypeByRadius
import com.mmfsin.streetparking.presentation.core.components.LoadingFullScreen
import com.mmfsin.streetparking.presentation.core.components.MediumText
import com.mmfsin.streetparking.presentation.core.theme.BlueMedium
import com.mmfsin.streetparking.presentation.core.theme.BlueTransparent
import com.mmfsin.streetparking.presentation.core.theme.White
import com.mmfsin.streetparking.presentation.map.components.LocationErrorDialog
import com.mmfsin.streetparking.presentation.map.components.NeedsLocationOn
import com.mmfsin.streetparking.presentation.map.components.OnResume
import com.mmfsin.streetparking.presentation.map.components.RadiusDialog
import com.mmfsin.streetparking.presentation.map.components.RequestLocationPermissions
import com.mmfsin.streetparking.presentation.map.components.checkLocationPermissions
import com.mmfsin.streetparking.presentation.map.helper.dialogEnableGps
import com.mmfsin.streetparking.presentation.map.helper.getUserLocation
import com.mmfsin.streetparking.presentation.map.helper.isGPSActive

@Preview
@Composable
fun MapScreenPV() {
    MapContent(
        uiState = MapStates(
            hasLocationPermission = false,
            isGPSActive = false,
            showRadiusDialog = true,
        ),
        {}, {}, {},
        {}, {},
    )
}

@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MapContent(
        uiState = uiState,
        updatePermissionsState = { state -> viewModel.updatePermissionsState(state) },
        updateUserLocation = { location -> viewModel.updateUserLocation(location) },
        updateGPSActive = { active -> viewModel.updateGPSActive(active) },
        updateShowRadiusDialog = { visible -> viewModel.showRadiusDialog(visible) },
        updateRadius = { newRadius -> viewModel.updateRadius(newRadius) },
    )
}

@Composable
fun MapContent(
    uiState: MapStates,
    updatePermissionsState: (Boolean) -> Unit,
    updateUserLocation: (LatLng) -> Unit,
    updateGPSActive: (Boolean) -> Unit,
    updateShowRadiusDialog: (Boolean) -> Unit,
    updateRadius: (Double) -> Unit,
) {

    val context = LocalContext.current
    val activity = context as Activity

    var showGPSDialog by remember { mutableStateOf(true) }

    OnResume {
        val granted = checkLocationPermissions(context)
        updatePermissionsState(granted)

        if (granted) {
            val gpsActive = isGPSActive(context)
            updateGPSActive(gpsActive)

            if (!gpsActive && showGPSDialog) {
                showGPSDialog = false
                dialogEnableGps(context, activity)
            }

            if (gpsActive) {
                getUserLocation(
                    context = context,
                    userLocation = { updateUserLocation(it) },
                )
            }
        }
    }

    val hasPermissionsGranted = checkLocationPermissions(context)

    if (hasPermissionsGranted) updatePermissionsState(true)
    else RequestLocationPermissions { isGranted ->
        if (isGranted) updatePermissionsState(true)
        else updatePermissionsState(false)
    }

    if (!uiState.hasLocationPermission) {
        LocationErrorDialog()
        return
    }

    if (uiState.isGPSActive == false) {
        NeedsLocationOn()
        return
    }

    if (uiState.userLocation == null) LoadingFullScreen()
    uiState.userLocation?.let { location ->
        Box(Modifier.fillMaxSize()) {

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(location, 16f)
                },
                properties = MapProperties(
                    isMyLocationEnabled = true
                )
            ) {
                Circle(
                    center = location,
                    radius = uiState.radius,
                    fillColor = BlueTransparent,
                    strokeColor = BlueMedium,
                    strokeWidth = 1f
                )
            }


            ButtonRadius(
                radius = uiState.radius,
                onClick = { updateShowRadiusDialog(true) }
            )
        }
    }

    if (uiState.showRadiusDialog) RadiusDialog(
        actualRadius = uiState.radius,
        onDismiss = { updateShowRadiusDialog(false) },
        newRadius = { newRadius -> updateRadius(newRadius) }
    )
}

@Preview
@Composable
fun ButtonRadiusPV() = ButtonRadius(1000.0, onClick = {})

@Composable
fun ButtonRadius(
    radius: Double,
    onClick: () -> Unit
) {
    val actualRadius = getTypeByRadius(radius)
    Card(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .padding(top = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .zIndex(1f)
                .shadow(
                    elevation = 4.dp,
                    clip = false
                )
                .clickable(onClick = { onClick() })
                .background(White)
                .padding(vertical = 4.dp, horizontal = 6.dp)
        ) {
            MediumText(text = R.string.radius_dialog_search)
            Spacer(Modifier.width(4.dp))
            MediumText(text = actualRadius.text, fontWeight = FontWeight.SemiBold)
        }
    }
}