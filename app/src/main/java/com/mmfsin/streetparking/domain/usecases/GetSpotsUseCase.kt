package com.mmfsin.streetparking.domain.usecases

import com.mmfsin.streetparking.domain.models.Spot
import javax.inject.Inject

class GetSpotsUseCase @Inject constructor() {
    operator fun invoke(): List<Spot> {
        return listOf(
            Spot("A", 40.395228, -3.710444, "Calle Salmonela", 12425254, 27),
            Spot("B", 40.395780, -3.700851, "Calle Salmonela 2", 124254, 27),
            Spot("C", 40.397043, -3.715447, "Calle Salmonela 3", 124269, 27),
        )
    }
}