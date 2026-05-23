package com.mmfsin.streetparking.presentation.home

import com.mmfsin.streetparking.domain.models.Spot

data class HomeStates(
    val spots: List<Spot> = emptyList()
)