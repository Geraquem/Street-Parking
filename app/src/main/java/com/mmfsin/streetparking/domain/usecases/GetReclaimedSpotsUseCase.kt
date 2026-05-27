package com.mmfsin.streetparking.domain.usecases

import com.mmfsin.streetparking.domain.interfaces.ISpotsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReclaimedSpotsUseCase @Inject constructor(
    private val repository: ISpotsRepository
) {
    operator fun invoke(): Flow<List<String>> = repository.getReclaimedSpots()
}
