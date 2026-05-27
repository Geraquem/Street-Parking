package com.mmfsin.streetparking.presentation.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun SoftwareKeyboardController.closeKeyboard(focusManager: FocusManager) {
    this.hide()
    focusManager.clearFocus()
}

fun <T1 : Any, T2 : Any, R : Any> checkNotNulls(p1: T1?, p2: T2?, block: (T1, T2) -> R): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun Long.formatDate(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
        .withZone(ZoneId.systemDefault())

    return formatter.format(Instant.ofEpochMilli(this))
        .replaceFirstChar { it.uppercase() }
}

fun Context.getAddress(lat: Double, lng: Double, result: (String?) -> Unit) {
    val maxResults = 1
    val geocoder = Geocoder(this, Locale.getDefault())
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        geocoder.getFromLocation(
            lat, lng, maxResults,
            object : Geocoder.GeocodeListener {
                override fun onError(errorMessage: String?) {
                    super.onError(errorMessage)
                    result(null)
                }

                override fun onGeocode(p0: List<Address?>) {
                    result(p0.firstOrNull().completeAddress())
                }
            })
    } else {
        try {
            val address = geocoder.getFromLocation(lat, lng, maxResults)?.firstOrNull()
            address?.getAddressLine(0)
            result(address.completeAddress())
        } catch (e: Exception) {
            result(null)
        }
    }
}

private fun Address?.completeAddress(): String? {
    return this?.let { adr ->
        val aa = filterDirection(adr.thoroughfare, adr.subThoroughfare)
        println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")
        println("address $aa")
        println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")
        aa
    } ?: run { null }
}

private fun filterDirection(vararg parts: String?): String =
    parts.filter { !it.isNullOrBlank() && it != "null" }.joinToString(", ")