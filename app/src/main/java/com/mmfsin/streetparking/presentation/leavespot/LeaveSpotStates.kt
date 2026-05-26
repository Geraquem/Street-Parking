package com.mmfsin.streetparking.presentation.leavespot

import com.mmfsin.streetparking.domain.models.Spot

data class LeaveSpotStates(
    val spots: List<Spot> = emptyList()
)