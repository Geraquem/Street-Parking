package com.mmfsin.streetparking.data.mappers

import com.mmfsin.streetparking.data.models.SpotDTO
import com.mmfsin.streetparking.data.models.SpotIdDTO
import com.mmfsin.streetparking.domain.models.Spot

fun SpotDTO.toSpot() = Spot(
    id = id,
    lat = lat,
    lng = lng,
    date = date,
    reclaimed = reclaimed
)

fun List<SpotDTO>.toSpotList() = this.map { it.toSpot() }

fun List<SpotIdDTO>.toIds() = this.map { it.id }