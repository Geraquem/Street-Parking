package com.mmfsin.streetparking.presentation.leavespot

import com.mmfsin.streetparking.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LeaveSpotViewModel @Inject constructor(

) : BaseViewModel<LeaveSpotStates>(LeaveSpotStates()) {

    init {

    }
}