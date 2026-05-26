package com.mmfsin.streetparking.domain.models

data class Spot(
    val id: String,
    val lat: Double,
    val lng: Double,
    val address: String,
    val date: Long,
    val reclaimed: Long,
)