package com.mmfsin.streetparking.presentation.main

import com.mmfsin.streetparking.domain.usecases.GetLastScreenUseCase
import com.mmfsin.streetparking.domain.usecases.UpdateLastScreenUseCase
import com.mmfsin.streetparking.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLastScreenUseCase: GetLastScreenUseCase,
    private val updateLastScreenUseCase: UpdateLastScreenUseCase,
) : BaseViewModel<MainStates>(MainStates()) {

    init {
        getLastScreen()
    }

    fun navDrawerOpened(opened: Boolean) {
        _uiState.update { it.copy(isDrawerOpened = opened) }
    }

    fun getLastScreen() {
        executeUseCase(
            { getLastScreenUseCase() },
            { screen -> _uiState.update { it.copy(lastScreen = screen) } },
            {}
        )
    }

    fun updateLastScreen(screen: String) {
        executeUseCase(
            { updateLastScreenUseCase(screen) },
            {},
            {}
        )
    }
}