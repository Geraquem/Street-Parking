package com.mmfsin.streetparking.domain.usecases

import com.mmfsin.streetparking.domain.interfaces.IConfigRepository
import javax.inject.Inject

class GetRadiusUseCase @Inject constructor(val repository: IConfigRepository) {
    operator fun invoke() = repository.getRadius()
}