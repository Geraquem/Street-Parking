package com.mmfsin.streetparking.domain.interfaces

import com.google.android.gms.maps.model.LatLng
import com.mmfsin.streetparking.domain.models.Spot

interface ISpotsRepository {
    suspend fun createSpot(coordinates: LatLng): Result<Unit>
    suspend fun getSpotsAroundMe(userLocation: LatLng): List<Spot>
    suspend fun reclaimSpot(id: String)
}