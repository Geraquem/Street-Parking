package com.mmfsin.streetparking.domain.usecases

import com.mmfsin.streetparking.domain.interfaces.IConfigRepository
import javax.inject.Inject

class UpdateRadiusUseCase @Inject constructor(val repository: IConfigRepository) {
    operator fun invoke(newRadius: Double) = repository.updateRadius(newRadius)
}