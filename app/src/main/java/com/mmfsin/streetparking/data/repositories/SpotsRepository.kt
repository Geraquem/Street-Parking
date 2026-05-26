package com.mmfsin.streetparking.data.repositories

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.mmfsin.streetparking.data.bbdd.daos.SpotsDAO
import com.mmfsin.streetparking.data.mappers.createSpotByCoordinates
import com.mmfsin.streetparking.domain.interfaces.ISpotsRepository
import com.mmfsin.streetparking.domain.models.Spot
import com.mmfsin.streetparking.presentation.utils.SPOTS
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

    override fun getSpotsAroundMe(): List<Spot> {
        val result = mutableListOf<Spot>()
        //
        //        val db = FirebaseFirestore.getInstance()
        //        db.collection(DILEMMAS).get().addOnSuccessListener { documents ->
        //            for (doc in documents) {
        //                val data = Dilemma(
        //                    topText = doc.getString(TXT_TOP) ?: "",
        //                    bottomText = doc.getString(TXT_BOTTOM) ?: "",
        //                    creator = doc.getString(CREATOR_NAME) ?: "",
        //                    votesYes = 456,
        //                    votesNo = 851
        //                )
        //                result.add(data)
        //            }
        //            cont.resume(result)
        //        }.addOnFailureListener {
        //            cont.resume(null)
        //        }
        return result
    }
}