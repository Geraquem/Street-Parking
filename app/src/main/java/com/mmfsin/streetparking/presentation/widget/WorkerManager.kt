package com.mmfsin.streetparking.presentation.widget

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.mmfsin.streetparking.data.models.SpotDTO
import java.util.UUID

class AddSpotWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        val widgetId = inputData.getInt("widgetId", -1)

        return try {

            FirebaseFirestore.getInstance().collection("aux")
                .document(UUID.randomUUID().toString())
                .set(SpotDTO())

            Result.success().also {
//                updateWidgetSuccess(applicationContext, widgetId)
            }

        } catch (e: Exception) {
//            updateWidgetError(applicationContext, widgetId)
            Result.failure()
        }
    }
}