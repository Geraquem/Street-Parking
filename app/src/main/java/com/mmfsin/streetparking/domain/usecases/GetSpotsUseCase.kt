package com.mmfsin.streetparking.domain.usecases

import com.mmfsin.streetparking.domain.models.Spot
import javax.inject.Inject

class GetSpotsUseCase @Inject constructor() {
    operator fun invoke(): List<Spot> {
        return listOf(
            Spot("A", 40.395228, -3.710444, 12425254, 27),
            Spot("B", 40.395780, -3.700851, 124254, 27),
            Spot("C", 40.397043, -3.715447, 124269, 27),
            Spot("D", 40.392925, -3.708986, 124269, 27),
            Spot("E", 40.393456, -3.709455, 124269, 27),
            Spot("F", 40.396440, -3.707701, 124269, 27),
            Spot("G", 40.392801, -3.702259, 124269, 27),
            Spot("H", 40.393930, -3.698981, 124269, 27),
            Spot("I", 40.394156, -3.711954, 124269, 27),
            Spot("J", 40.391405, -3.706135, 124269, 27),
        )
    }
}