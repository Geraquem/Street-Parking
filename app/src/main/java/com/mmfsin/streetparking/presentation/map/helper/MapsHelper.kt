package com.mmfsin.streetparking.presentation.map.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.core.net.toUri
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource

fun dialogEnableGps(
    context: Context,
    activity: Activity,
) {
    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        1000L
    ).build()

    val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    val settingsClient = LocationServices.getSettingsClient(context)
    val task = settingsClient.checkLocationSettings(builder.build())

    task.addOnSuccessListener { }
    task.addOnCanceledListener { }
    task.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                exception.startResolutionForResult(
                    activity,
                    1001
                )
            } catch (_: Exception) {
            }
        }
    }
}

fun isGPSActive(context: Context): Boolean {
    val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

fun getUserLocation(
    context: Context,
    userLocation: (LatLng) -> Unit,
) = getLocation(context, onLocation = { userLocation(it) })

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
    }.addOnFailureListener {
        println("Failure obtaining Location: ${it.message}")
    }
}

fun Context.howToGo(lat: Double, lng: Double) {
    val gmmIntentUri = "google.navigation:q=$lat,$lng&mode=d".toUri()
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")

    startActivity(mapIntent)
}