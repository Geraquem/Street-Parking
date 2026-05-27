package com.mmfsin.streetparking.presentation.onboarding

import com.mmfsin.streetparking.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(

) : BaseViewModel<OnBoardingStates>(OnBoardingStates()) {


}