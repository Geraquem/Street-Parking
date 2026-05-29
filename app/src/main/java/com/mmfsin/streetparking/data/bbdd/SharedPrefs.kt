package com.mmfsin.streetparking.data.bbdd

import android.content.SharedPreferences
import androidx.core.content.edit
import com.mmfsin.streetparking.presentation.utils.LEAVE_SPOT_SCREEN
import com.mmfsin.streetparking.presentation.utils.SP_LAST_SCREEN
import com.mmfsin.streetparking.presentation.utils.SP_RADIUS
import javax.inject.Inject

class SharedPrefs @Inject constructor(
    private val prefs: SharedPreferences
) {
    fun getLastScreen(): String? = prefs.getString(SP_LAST_SCREEN, LEAVE_SPOT_SCREEN)
    fun updateLastScreen(value: String) = prefs.edit { putString(SP_LAST_SCREEN, value) }

    fun getRadius(): String? = prefs.getString(SP_RADIUS, "1000.0")
    fun updateRadius(value: String) = prefs.edit { putString(SP_RADIUS, value) }
}