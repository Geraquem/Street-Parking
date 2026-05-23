package com.mmfsin.streetparking.data.repositories

import com.mmfsin.streetparking.data.bbdd.SharedPrefs
import com.mmfsin.streetparking.domain.interfaces.IConfigRepository
import javax.inject.Inject

class ConfigRepository @Inject constructor(
    val sharedPrefs: SharedPrefs
) : IConfigRepository {

}