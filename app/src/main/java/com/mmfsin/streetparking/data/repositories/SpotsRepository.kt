package com.mmfsin.streetparking.data.repositories

import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mmfsin.streetparking.data.bbdd.daos.SpotsDAO
import com.mmfsin.streetparking.data.mappers.createSpotByCoordinates
import com.mmfsin.streetparking.data.mappers.toSpotList
import com.mmfsin.streetparking.data.models.SpotDTO
import com.mmfsin.streetparking.domain.interfaces.ISpotsRepository
import com.mmfsin.streetparking.domain.models.Spot
import com.mmfsin.streetparking.presentation.utils.GEOHASH
import com.mmfsin.streetparking.presentation.utils.RECLAIMED
import com.mmfsin.streetparking.presentation.utils.SPOTS
import com.mmfsin.streetparking.presentation.utils.checkNotNulls
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class SpotsRepository @Inject constructor(
    val spotsDao: SpotsDAO
) : ISpotsRepository {

    override suspend fun createSpot(coordinates: LatLng): Result<Unit> {

        insertDataInFirestore()
        return Result.success(Unit)

        //        return try {
        //            val id = UUID.randomUUID().toString()
        //            val spot = coordinates.createSpotByCoordinates(id)
        //
        //            FirebaseFirestore.getInstance()
        //                .collection(SPOTS)
        //                .document(id)
        //                .set(spot)
        //                .await()
        //            Result.success(Unit)
        //
        //        } catch (e: Exception) {
        //            Result.failure(e)
        //        }
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


    override suspend fun reclaimSpot(id: String) {
        FirebaseFirestore.getInstance()
            .collection(SPOTS)
            .document(id)
            .update(RECLAIMED, FieldValue.increment(1))
            .await()
    }

    override suspend fun deleteSpot(id: String) {
        FirebaseFirestore.getInstance().collection(SPOTS).document(id).delete()
    }

    /*************************************************************************************/
    /*************************************************************************************/
    /*************************************************************************************/

    suspend fun insertDataInFirestore() {
        val db = FirebaseFirestore.getInstance()
        val batch = db.batch()

        val listToInsert = listToInsert()
        val usersCollection = db.collection(SPOTS)

        for (data in listToInsert) {
            val id = data.id
            val newDocRef = usersCollection.document(id)
            batch.set(newDocRef, data)
        }

        batch.commit().await()
    }


    private fun listToInsert(): List<SpotDTO> {
        return listOf(
            LatLng(40.396113, -3.710846).createSpotByCoordinates(UUID.randomUUID().toString()),
            LatLng(40.392822, -3.713133).createSpotByCoordinates(UUID.randomUUID().toString()),
            LatLng(40.388555, -3.707909).createSpotByCoordinates(UUID.randomUUID().toString()),
            LatLng(40.392065, -3.729403).createSpotByCoordinates(UUID.randomUUID().toString()),
            LatLng(40.398640, -3.706744).createSpotByCoordinates(UUID.randomUUID().toString()),
            LatLng(40.395235, -3.700088).createSpotByCoordinates(UUID.randomUUID().toString()),
            LatLng(40.391621, -3.703426).createSpotByCoordinates(UUID.randomUUID().toString()),
            LatLng(40.393242, -3.707626).createSpotByCoordinates(UUID.randomUUID().toString()),
            LatLng(40.400073, -3.710833).createSpotByCoordinates(UUID.randomUUID().toString()),
            LatLng(40.394183, -3.706777).createSpotByCoordinates(UUID.randomUUID().toString()),
//            LatLng(aaaaaaaaaa).createSpotByCoordinates(UUID.randomUUID().toString()),
        )
    }
}