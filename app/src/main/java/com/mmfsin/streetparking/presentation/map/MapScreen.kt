@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.streetparking.presentation.map

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SheetValue.Hidden
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.domain.models.RadiusTypes.Companion.getTypeByRadius
import com.mmfsin.streetparking.domain.models.Spot
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
import com.mmfsin.streetparking.presentation.map.components.SpotSheet
import com.mmfsin.streetparking.presentation.map.components.checkLocationPermissions
import com.mmfsin.streetparking.presentation.map.helper.dialogEnableGps
import com.mmfsin.streetparking.presentation.map.helper.getUserLocation
import com.mmfsin.streetparking.presentation.map.helper.isGPSActive
import kotlinx.coroutines.launch

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
        {}, {}, {}, {},
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
        getSpots = { viewModel.getSpots() },
        updateSelectedSpot = { spot -> viewModel.updateSelectedSpot(spot) },
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
    getSpots: () -> Unit,
    updateSelectedSpot: (Spot?) -> Unit,
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
        LaunchedEffect(location) { getSpots() }

        val sheetState = rememberStandardBottomSheetState(
            initialValue = Hidden,
            skipHiddenState = false
        )
        val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)

        val scope = rememberCoroutineScope()

        val cameraPositionState = rememberCameraPositionState()

        LaunchedEffect(uiState.selectedSpot, location) {
            val target = uiState.selectedSpot?.let {
                LatLng(it.lat, it.lng)
            } ?: location

            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(target, 16f),
                durationMs = 800
            )
        }

        Box(Modifier.fillMaxSize()) {

            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetPeekHeight = 0.dp,
                sheetDragHandle = { },
                sheetContent = {
                    if (uiState.selectedSpot != null) {
                        SpotSheet(
                            spot = uiState.selectedSpot,
                            {
                                scope.launch { scaffoldState.bottomSheetState.hide() }
//                                updateSelectedSpot(null)
                            },
                            {}
                        )
                    } else {
                        Box(modifier = Modifier.fillMaxWidth().height(1.dp))
                    }
                }
            ) { padding ->

                GoogleMap(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    cameraPositionState = cameraPositionState,
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

                    uiState.spots.forEach { spot ->
                        Marker(
                            state = MarkerState(
                                position = LatLng(spot.lat, spot.lng)
                            ),
                            onClick = {
                                scope.launch { scaffoldState.bottomSheetState.expand() }
                                updateSelectedSpot(spot)
                                true
                            }
                        )
                    }
                }
            }

            if (scaffoldState.bottomSheetState.currentValue != SheetValue.Expanded) {
                ButtonRadius(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    radius = uiState.radius,
                    onClick = { updateShowRadiusDialog(true) }
                )
            }
        }
    }

    if (uiState.showRadiusDialog) {
        RadiusDialog(
            actualRadius = uiState.radius,
            onDismiss = { updateShowRadiusDialog(false) },
            newRadius = { newRadius -> updateRadius(newRadius) }
        )
    }
}

@Preview
@Composable
fun ButtonRadiusPV() = ButtonRadius(Modifier, 1000.0, onClick = {})

@Composable
fun ButtonRadius(
    modifier: Modifier,
    radius: Double,
    onClick: () -> Unit
) {
    val actualRadius = getTypeByRadius(radius)
    Column(modifier = modifier.padding(8.dp)) {
        Surface(
            shape = RoundedCornerShape(50),
            shadowElevation = 6.dp,
            color = White
        ) {
            Row(
                modifier = Modifier
                    .clickable(onClick = { onClick() })
                    .background(White)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                MediumText(text = R.string.radius_dialog_search)
                Spacer(Modifier.width(4.dp))
                MediumText(text = actualRadius.text, fontWeight = FontWeight.SemiBold)
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}