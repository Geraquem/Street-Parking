package com.mmfsin.streetparking.domain.usecases

import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.maps.model.LatLng
import com.mmfsin.streetparking.domain.interfaces.ISpotsRepository
import javax.inject.Inject

class ReclaimSpotUseCase @Inject constructor(
    private val repository: ISpotsRepository
) {
    suspend operator fun invoke(id: String, spotLocation: LatLng, userLocation: LatLng) {
        val distance = GeoFireUtils.getDistanceBetween(
            GeoLocation(spotLocation.latitude, spotLocation.longitude),
            GeoLocation(userLocation.latitude, userLocation.longitude)
        )

        if (distance < 30) repository.deleteSpot(id)
        else repository.reclaimSpot(id)
    }
}
