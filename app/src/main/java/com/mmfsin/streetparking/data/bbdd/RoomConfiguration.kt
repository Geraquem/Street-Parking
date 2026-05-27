package com.mmfsin.streetparking.data.bbdd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmfsin.streetparking.data.bbdd.daos.SpotsDAO
import com.mmfsin.streetparking.data.models.SpotIdDTO

@Database(entities = [SpotIdDTO::class], version = 1)
abstract class RoomConfiguration : RoomDatabase() {
    abstract fun productsDAO(): SpotsDAO
}