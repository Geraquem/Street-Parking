package com.mmfsin.streetparking.domain.interfaces

import com.google.android.gms.maps.model.LatLng
import com.mmfsin.streetparking.domain.models.Spot
import kotlinx.coroutines.flow.Flow

interface ISpotsRepository {
    suspend fun createSpot(coordinates: LatLng): Result<Unit>
    suspend fun getSpotsAroundMe(userLocation: LatLng): List<Spot>
    suspend fun deleteSpot(id: String)

    fun getReclaimedSpots(): Flow<List<String>>
    suspend fun reclaimSpot(id: String)
}