package com.mmfsin.streetparking.domain.usecases

import com.google.android.gms.maps.model.LatLng
import com.mmfsin.streetparking.domain.interfaces.ISpotsRepository
import javax.inject.Inject

class CreateSpotUseCase @Inject constructor(val repository: ISpotsRepository) {
    suspend operator fun invoke(coordinates: LatLng) = repository.createSpot(coordinates)
}