package com.mmfsin.streetparking.data.repositories

import com.mmfsin.streetparking.data.bbdd.daos.SpotsDAO
import com.mmfsin.streetparking.domain.interfaces.ISpotsRepository
import javax.inject.Inject

class SpotsRepository @Inject constructor(
    val spotsDao: SpotsDAO
) : ISpotsRepository {

}