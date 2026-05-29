package com.mmfsin.streetparking.domain.usecases

import com.mmfsin.streetparking.domain.interfaces.IConfigRepository
import javax.inject.Inject

class UpdateLastScreenUseCase @Inject constructor(
    private val repository: IConfigRepository
) {
    operator fun invoke(screen: String) = repository.updateLastScreen(screen)
}
