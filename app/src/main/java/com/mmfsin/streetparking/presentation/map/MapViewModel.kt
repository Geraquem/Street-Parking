package com.mmfsin.streetparking.presentation.map

import com.mmfsin.streetparking.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(

) : BaseViewModel<MapStates>(MapStates()) {

    init {

    }
}