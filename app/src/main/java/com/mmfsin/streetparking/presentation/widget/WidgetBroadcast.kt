package com.mmfsin.streetparking.presentation.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class WidgetBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        println("Widget pulsadoooooooooooooooooooooooooooooooooooooooo")
        Toast.makeText(context, "Widget pulsado!", Toast.LENGTH_SHORT).show()
    }
}