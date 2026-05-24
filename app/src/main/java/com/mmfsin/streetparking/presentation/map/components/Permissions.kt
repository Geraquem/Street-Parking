package com.mmfsin.streetparking.presentation.map.components

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat.checkSelfPermission

fun checkLocationPermissions(context: Context): Boolean {
    val fine = checkSelfPermission(context, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
    val coarse = checkSelfPermission(context, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED
    return fine || coarse
}

@Composable
fun RequestLocationPermissions(
    onResult: (Boolean) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fine = permissions[ACCESS_FINE_LOCATION] ?: false
        val coarse = permissions[ACCESS_COARSE_LOCATION] ?: false

        onResult(fine || coarse)
    }

    LaunchedEffect(Unit) {
        launcher.launch(
            arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            )
        )
    }
}