package com.mmfsin.streetparking.presentation.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.RemoteViews
import com.mmfsin.streetparking.R
import com.mmfsin.streetparking.presentation.widget.WidgetRender.WidgetState
import com.mmfsin.streetparking.presentation.widget.WidgetRender.render

class WidgetProvider : AppWidgetProvider() {

    companion object {
        const val ON_WIDGET_CLICK = "on_widget_click"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { widgetId ->
            val intent = Intent(context, WidgetProvider::class.java).apply {
                action = ON_WIDGET_CLICK
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            }

            val pending = PendingIntent.getBroadcast(
                context,
                widgetId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val views = RemoteViews(context.packageName, R.layout.widget_layout).apply {
                setOnClickPendingIntent(R.id.widget_button, pending)
            }

            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action == ON_WIDGET_CLICK) {

            val widgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                -1
            )

            if (widgetId == -1) return

            render(context, widgetId, WidgetState.LOADING)

            Handler(Looper.getMainLooper()).postDelayed({
                render(context, widgetId, WidgetState.IDLE)
            }, 2000)
        }
    }
}