package com.mmfsin.streetparking.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.streetparking.presentation.utils.TABLE_SPOTS

@Entity(tableName = TABLE_SPOTS)
data class SpotDTO(
    @PrimaryKey val id: String = "",
    val lat: Long = 0,
    val lng: Long = 0,
    val reclaimed: Long = 0,
    val date: Long = 0,
)