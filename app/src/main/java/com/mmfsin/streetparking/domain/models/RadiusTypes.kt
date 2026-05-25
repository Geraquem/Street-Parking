package com.mmfsin.streetparking.domain.models

import com.mmfsin.streetparking.R

enum class RadiusTypes(val radius: Double, val text: Int) {
    TWO_FIFTY_METERS(250.0, R.string.radius_250_m),
    FIVE_HUNDRED__METERS(500.0, R.string.radius_500_m),
    SEVENTY_FIVE_HUNDRED_METERS(750.0, R.string.radius_750_m),
    ONE_KILOMETER(1000.0, R.string.radius_1_km),
    ONE_FIVE_HUNDRED_KILOMETERS(1500.0, R.string.radius_1_5_km),
    TWO_KILOMETERS(2000.0, R.string.radius_2_km),
    TWO_FIVE_HUNDRED_KILOMETERS(2500.0, R.string.radius_2_5_km),
    THREE_KILOMETERS(3000.0, R.string.radius_3_km);

    companion object {
        fun getAllRadius() = entries
        fun getTypeByRadius(radius: Double): RadiusTypes = entries.find { it.radius == radius } ?: ONE_KILOMETER
    }
}