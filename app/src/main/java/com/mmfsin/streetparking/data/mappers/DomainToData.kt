package com.mmfsin.streetparking.data.mappers

import com.google.android.gms.maps.model.LatLng
import com.mmfsin.streetparking.domain.models.Spot

fun LatLng.createSpotByCoordinates(id: String) = Spot(
    id = id,
    lat = latitude,
    lng = longitude,
    date = System.currentTimeMillis(),
    reclaimed = 0
)
