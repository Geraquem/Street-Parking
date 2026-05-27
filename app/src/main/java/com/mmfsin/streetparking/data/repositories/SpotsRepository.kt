package com.mmfsin.streetparking.data.repositories

import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.mmfsin.streetparking.data.bbdd.daos.SpotsDAO
import com.mmfsin.streetparking.data.mappers.createSpotByCoordinates
import com.mmfsin.streetparking.data.mappers.toSpotList
import com.mmfsin.streetparking.data.models.SpotDTO
import com.mmfsin.streetparking.domain.interfaces.ISpotsRepository
import com.mmfsin.streetparking.domain.models.Spot
import com.mmfsin.streetparking.presentation.utils.GEOHASH
import com.mmfsin.streetparking.presentation.utils.SPOTS
import com.mmfsin.streetparking.presentation.utils.checkNotNulls
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class SpotsRepository @Inject constructor(
    val spotsDao: SpotsDAO
) : ISpotsRepository {

    override suspend fun createSpot(coordinates: LatLng): Result<Unit> {
        return try {
            val id = UUID.randomUUID().toString()
            val spot = coordinates.createSpotByCoordinates(id)

            FirebaseFirestore.getInstance()
                .collection(SPOTS)
                .document(id)
                .set(spot)
                .await()
            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getSpotsAroundMe(userLocation: LatLng): List<Spot> {
        val center = GeoLocation(userLocation.latitude, userLocation.longitude)
        val radiusInM = 1500.0
        val bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM)

        val db = FirebaseFirestore.getInstance()

        val tasks = bounds.map { b ->
            db.collection(SPOTS)
                .orderBy(GEOHASH)
                .startAt(b.startHash)
                .endAt(b.endHash)
                .get()
                .await()
        }

        val matching = mutableListOf<SpotDTO>()

        for (snap in tasks) {
            for (doc in snap.documents) {
                val lat = doc.getDouble("lat")
                val lng = doc.getDouble("lng")

                checkNotNulls(lat, lng) { lat, lng ->
                    val distance = GeoFireUtils.getDistanceBetween(
                        center,
                        GeoLocation(lat, lng)
                    )

                    if (distance <= radiusInM) {
                        doc.toObject(SpotDTO::class.java)?.let { matching.add(it) }
                    }
                }
            }
        }

        return matching.distinctBy { it.id }.toSpotList()
    }


    //    override fun getSpotsAroundMe(): List<Spot> {
    //        val result = mutableListOf<SpotDTO>()
    //        val db = FirebaseFirestore.getInstance()
    //        db.collection(SPOTS).get().addOnSuccessListener { documents ->
    //            for (doc in documents) {
    //                val eventEntity = doc.toObject(EventDTO::class.java)
    //                result.add(eventEntity.toEvent())
    //            }
    //            cont.resume(result)
    //        }.addOnFailureListener {
    //            cont.resume(null)
    //        }
    //    }
}