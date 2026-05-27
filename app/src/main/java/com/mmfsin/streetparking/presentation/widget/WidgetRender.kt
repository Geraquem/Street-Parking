package com.mmfsin.streetparking.presentation.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.RemoteViews
import com.mmfsin.streetparking.R

object WidgetRender {

    object WidgetState {
        const val IDLE = 0
        const val LOADING = 1
        const val SUCCESS = 2
        const val ERROR = 3
    }

    fun render(
        context: Context,
        widgetId: Int,
        state: Int
    ) {
        val views = RemoteViews(
            context.packageName,
            R.layout.widget_layout
        )

        when (state) {

            WidgetState.IDLE -> {
                views.setImageViewResource(R.id.widget_button, R.drawable.ic_map_spot)
                views.setTextViewText(R.id.widget_text, "Añadir spot")
            }

            WidgetState.LOADING -> {
                views.setImageViewResource(R.id.widget_button, R.drawable.ic_car)
                views.setTextViewText(R.id.widget_text, "Guardando...")
            }

            WidgetState.SUCCESS -> {
                views.setImageViewResource(R.id.widget_button, R.drawable.ic_check)
                views.setTextViewText(R.id.widget_text, "✔ OK")
            }

            WidgetState.ERROR -> {
                views.setImageViewResource(R.id.widget_button, R.drawable.ic_arrow_back)
                views.setTextViewText(R.id.widget_text, "❌ Error")
            }
        }

        AppWidgetManager.getInstance(context).updateAppWidget(widgetId, views)
    }

    fun showSuccessThenReset(context: Context, widgetId: Int) {

        render(context, widgetId, WidgetState.SUCCESS)

        Handler(Looper.getMainLooper()).postDelayed({
            render(context, widgetId, WidgetState.IDLE)
        }, 2000)
    }

    fun showErrorThenReset(context: Context, widgetId: Int) {

        render(context, widgetId, WidgetState.ERROR)

        Handler(Looper.getMainLooper()).postDelayed({
            render(context, widgetId, WidgetState.IDLE)
        }, 2000)
    }
}