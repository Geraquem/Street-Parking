package com.mmfsin.streetparking.presentation.main

import com.mmfsin.streetparking.presentation.utils.LOADING_SCREEN

data class MainStates(
    val isDrawerOpened: Boolean = false,
    val lastScreen: String = LOADING_SCREEN,
    val lastScreenIndex: Int = -1
)