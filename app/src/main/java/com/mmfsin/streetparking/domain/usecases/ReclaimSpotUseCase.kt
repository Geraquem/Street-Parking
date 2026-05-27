package com.mmfsin.streetparking.domain.usecases

import com.mmfsin.streetparking.domain.interfaces.ISpotsRepository
import javax.inject.Inject

class ReclaimSpotUseCase @Inject constructor(
    private val repository: ISpotsRepository
) {
    suspend operator fun invoke(id: String) = repository.reclaimSpot(id)
}
