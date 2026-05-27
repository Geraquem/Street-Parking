package com.mmfsin.streetparking.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.streetparking.presentation.utils.TABLE_RECLAIMED_SPOTS

@Entity(tableName = TABLE_RECLAIMED_SPOTS)
data class SpotIdDTO(
    @PrimaryKey val id: String = "",
)