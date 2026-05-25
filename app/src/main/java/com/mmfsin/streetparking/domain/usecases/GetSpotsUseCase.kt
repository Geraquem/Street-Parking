package com.mmfsin.streetparking.domain.usecases

import com.mmfsin.streetparking.domain.models.Spot
import javax.inject.Inject

class GetSpotsUseCase @Inject constructor() {
    operator fun invoke(): List<Spot> {
        return listOf(
            Spot("A", 40.4170, -3.7040, "Calle Salmonela", 12425254),
            Spot("B", 40.4500, -3.7500, "Calle Salmonela 2", 124254),
            Spot("C", 40.4200, -3.7000, "Calle Salmonela 3", 124269),
        )
    }
}