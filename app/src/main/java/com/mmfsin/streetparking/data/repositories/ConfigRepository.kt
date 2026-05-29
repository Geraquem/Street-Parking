package com.mmfsin.streetparking.data.repositories

import com.mmfsin.streetparking.data.bbdd.SharedPrefs
import com.mmfsin.streetparking.domain.interfaces.IConfigRepository
import com.mmfsin.streetparking.presentation.utils.LOADING_SCREEN
import javax.inject.Inject

class ConfigRepository @Inject constructor(
    val sharedPrefs: SharedPrefs
) : IConfigRepository {

    override fun getRadius(): Double = sharedPrefs.getRadius()?.toDouble() ?: 1000.0

    override fun updateRadius(newRadius: Double) {
        sharedPrefs.updateRadius(newRadius.toString())
    }

    override fun getLastScreen(): String = sharedPrefs.getLastScreen() ?: LOADING_SCREEN

    override fun updateLastScreen(lastScreen: String) {
        sharedPrefs.updateLastScreen(lastScreen)
    }
}