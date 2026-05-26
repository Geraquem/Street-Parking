package com.mmfsin.streetparking.data.mappers

import com.firebase.geofire.GeoFireUtils.getGeoHashForLocation
import com.firebase.geofire.GeoLocation
import com.google.android.gms.maps.model.LatLng
import com.mmfsin.streetparking.data.models.SpotDTO

fun LatLng.createSpotByCoordinates(id: String) = SpotDTO(
    id = id,
    lat = latitude,
    lng = longitude,
    geohash = getGeoHashForLocation(GeoLocation(latitude, longitude)),
    date = System.currentTimeMillis(),
    reclaimed = 0
)
