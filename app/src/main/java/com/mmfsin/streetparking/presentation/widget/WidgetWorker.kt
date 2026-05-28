package com.mmfsin.streetparking.presentation.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.widget.RemoteViews
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.mmfsin.streetparking.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class WidgetWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val id = inputData.getInt("widgetId", -1)

        updateWidgetState(id, "loading")

        val success = insertIntoFirebase()

        updateWidgetState(id, if (success) "success" else "error")

        delay(2000)

        updateWidgetState(id, "idle")

        return Result.success()
    }

    private suspend fun insertIntoFirebase(): Boolean =
        suspendCancellableCoroutine { cont ->
            FirebaseFirestore.getInstance().collection("test")
                .add(mapOf("timestamp" to System.currentTimeMillis()))
                .addOnSuccessListener { cont.resume(true) }
                .addOnFailureListener { cont.resume(false) }
        }

    private fun updateWidgetState(id: Int, state: String) {
        val views = RemoteViews(applicationContext.packageName, R.layout.widget_layout)

        println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")
        println("state: $state")
        println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")

        when (state) {
            "idle" -> views.setImageViewResource(R.id.widget_button, R.drawable.ic_map_spot)
            "loading" -> views.setImageViewResource(R.id.widget_button, R.drawable.ic_car)
            "success" -> views.setImageViewResource(R.id.widget_button, R.drawable.ic_check)
            "error" -> views.setImageViewResource(R.id.widget_button, R.drawable.ic_arrow_back)
        }

        AppWidgetManager.getInstance(applicationContext)
            .updateAppWidget(id, views)
    }
}