package com.mmfsin.streetparking.data.models

data class SpotDTO(
    val id: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val geohash: String = "",
    val reclaimed: Long = 0,
    val date: Long = 0,
)